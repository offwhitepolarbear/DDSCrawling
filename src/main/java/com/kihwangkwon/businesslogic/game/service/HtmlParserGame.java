package com.kihwangkwon.businesslogic.game.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.game.GameApi;
import com.kihwangkwon.businesslogic.game.domain.PlayerGameStat;
import com.kihwangkwon.businesslogic.game.domain.StarterPosition;
import com.kihwangkwon.businesslogic.game.domain.TeamGameStat;
import com.kihwangkwon.businesslogic.game.domain.TeamStat;

@Service
public class HtmlParserGame {
	
	GameApi gameApi;
	
	@Autowired
	public HtmlParserGame(GameApi gameApi) {
		this.gameApi = gameApi;
	}
	
	public List<TeamStat> getGame(String gameId) {
		Document doc = gameApi.getGameInfo(gameId);
		return getStatFromGameDocunment(doc, gameId);
 	}
	
	public List<TeamStat> getStatFromGameDocunment(Document document, String gameId){
		
		List<TeamStat> homeAwayStat= new ArrayList<TeamStat>();
		
		
		String awayName = document.select("div > font[face=roboto]").get(0).text();
		String homeName = document.select("div > font[face=roboto]").get(1).text();
		
		boolean overTime = isOverTime(document);
		
		Elements awayStarter = document.select("table.ui, .basic, .selectable, .table").get(0).select("tbody > tr[align=right]");
		Elements awayBench = document.select("table.ui, .basic, .selectable, .table").get(1).select("tbody > tr[align=right]").not("tr[style=font-weight:bold]");
		
		Elements awayTotal = document.select("table.ui, .basic, .selectable, .table").get(1).select("tbody > tr[style=font-weight:bold]");
		
		Elements homeStarter = document.select("table.ui, .basic, .selectable, .table").get(2).select("tbody > tr[align=right]");
		Elements homeBench = document.select("table.ui, .basic, .selectable, .table").get(3).select("tbody > tr[align=right]").not("tr[style=font-weight:bold]");
		
		Elements homeTotal = document.select("table.ui, .basic, .selectable, .table").get(3).select("tbody > tr[style=font-weight:bold]");
		// 원정 스타터
		List<PlayerGameStat> awayStaterList = playersStatConvert(awayStarter, gameId, awayName, homeName, false, true, overTime);
		// 원정 벤치 
		List<PlayerGameStat> awayBenchList = playersStatConvert(awayBench, gameId, awayName, homeName, false, false, overTime);
		// 홈 스타터
		List<PlayerGameStat> homeStaterList = playersStatConvert(homeStarter, gameId, homeName, awayName, true, true, overTime);
		// 홈 벤치
		List<PlayerGameStat> homeBenchList = playersStatConvert(homeBench, gameId, homeName, awayName, true, false, overTime);
		
		// 원정 팀
		TeamGameStat awayTeamStat = teamStatConvertor(awayTotal.get(0), gameId, awayName, homeName, false, overTime);
		// 홈 팀
		TeamGameStat homeTeamStat = teamStatConvertor(homeTotal.get(0), gameId, homeName, awayName, true, overTime);
		
		int homeMargin = homeTeamStat.getPoint()-awayTeamStat.getPoint();
		
		TeamStat homeTeam = new TeamStat();
		homeTeam.setTeamGameStat(homeTeamStat);
		homeStaterList.addAll(homeBenchList);
		homeTeam.setPlayerGameStatList(homeStaterList);

		TeamStat awayTeam = new TeamStat();
		awayTeam.setTeamGameStat(awayTeamStat);
		awayStaterList.addAll(awayBenchList);
		awayTeam.setPlayerGameStatList(awayStaterList);
		
		boolean win = true;
		boolean lose = false;
		
		if(homeMargin>0) {
			homeTeam = setTeamWin(homeTeam, win);
			awayTeam = setTeamWin(awayTeam, lose);
		}
		else {
			homeTeam = setTeamWin(homeTeam, lose);
			awayTeam = setTeamWin(awayTeam, win);
		}
		
		homeAwayStat.add(homeTeam);
		homeAwayStat.add(awayTeam);
		
		return homeAwayStat;
		
	}
	
	private TeamStat setTeamWin(TeamStat teamStat, boolean win) {
		
		TeamGameStat teamGameStat = teamStat.getTeamGameStat();
		teamGameStat.setWin(win);
		List<PlayerGameStat> players = teamStat.getPlayerGameStatList();
		
		for(PlayerGameStat playerGameStat : players) {
			playerGameStat.setWin(win);
		}

		return teamStat;
	}
	
	private List<PlayerGameStat> playersStatConvert(Elements players, String gameId, String teamName, String opponentTeamName, boolean isHome, boolean isStarter, boolean isOverTime) {
		List playerGameStatList = new ArrayList<>();
		Iterator<Element> player = players.iterator();
		int positionIndex=0;
		while(player.hasNext()) {
			String position = null;
			if(isStarter) {
				position = getPositionByIndex(positionIndex);
				positionIndex++;
			}
			PlayerGameStat playerGameStat = playerGameStatConvertor(player.next(), gameId, teamName, opponentTeamName, isHome, isStarter, isOverTime, position);
			playerGameStatList.add(playerGameStat);
		}
		return playerGameStatList;
	}

	private String getPositionByIndex(int positionIndex) {
		StarterPosition[] starterPositionList = StarterPosition.values();
		return starterPositionList[positionIndex].toString();
	}
	
	private PlayerGameStat playerGameStatConvertor(Element player, String gameId, String teamName, String opponentTeamName, boolean isHome, boolean isStarter, boolean isOverTime,String position) {
		if(position==null) {
			position = "bench";
		}
		int gameIdInt = Integer.parseInt(gameId);
		
		String playerId = player.select("tr > td> div > a").attr("href");
		
		//palyerId 뒤에 .hmtl 삭제
		playerId = playerId.substring(0,playerId.length()-5);
		
		
		int min = Integer.parseInt(player.select("tr > td").get(1).text());
		String[] filedGoal = player.select("tr > td").get(2).text().split("-");
		String[] threePoint = player.select("tr > td").get(3).text().split("-");
		String[] freeThrow = player.select("tr > td").get(4).text().split("-");
		int offensiveRebound = Integer.parseInt(player.select("tr > td").get(5).text());
		int defensiveRebound = Integer.parseInt(player.select("tr > td").get(6).text());
		int totalRebound = Integer.parseInt(player.select("tr > td").get(7).text());
		int assist = Integer.parseInt(player.select("tr > td").get(8).text());
		int steal = Integer.parseInt(player.select("tr > td").get(9).text());
		int block = Integer.parseInt(player.select("tr > td").get(10).text());
		int turnover = Integer.parseInt(player.select("tr > td").get(11).text());
		int foul = Integer.parseInt(player.select("tr > td").get(12).text());
		int point = Integer.parseInt(player.select("tr > td").get(13).text());
		int margin = Integer.parseInt(player.select("tr > td").get(14).text());
		
		int fieldGoalMade = Integer.parseInt(filedGoal[0]);
		int fieldGoalAttempt = Integer.parseInt(filedGoal[1]);
		int threePointMade = Integer.parseInt(threePoint[0]);
		int threePointAttempt = Integer.parseInt(threePoint[1]);
		int freeThrowMade = Integer.parseInt(freeThrow[0]);
		int freeThrowAttempt = Integer.parseInt(freeThrow[1]);
		
		PlayerGameStat playerGameStat = PlayerGameStat.builder()
			.gameNumber(gameIdInt)
			.teamName(teamName)
			.opponentTeamName(opponentTeamName)
			.home(isHome)
			.starter(isStarter)
			.playerId(playerId)
			.position(position)
			.min(min)
			.fieldGoalMade(fieldGoalMade)
			.fieldGoalAttempt(fieldGoalAttempt)
			.threePointMade(threePointMade)
			.threePointAttempt(threePointAttempt)
			.freeThrowMade(freeThrowMade)
			.freeThrowAttempt(freeThrowAttempt)
			.offensiveRebound(offensiveRebound)
			.defensiveRebound(defensiveRebound)
			.totalRebound(totalRebound)
			.assist(assist)
			.steal(steal)
			.block(block)
			.turnover(turnover)
			.foul(foul)
			.point(point)
			.margin(margin)
			.overtime(isOverTime)
			.build();
		return playerGameStat;
	}
	
	private TeamGameStat teamStatConvertor(Element team, String gameId, String teamName, String opponentTeamName, boolean isHome, boolean isOverTime) {
		
		int gameIdInt = Integer.parseInt(gameId);
		String[] filedGoal = team.select("tr > td").get(2).text().split("-");
		String[] threePoint = team.select("tr > td").get(3).text().split("-");
		String[] freeThrow = team.select("tr > td").get(4).text().split("-");
		int offensiveRebound = Integer.parseInt(team.select("tr > td").get(5).text());
		int defensiveRebound = Integer.parseInt(team.select("tr > td").get(6).text());
		int totalRebound = Integer.parseInt(team.select("tr > td").get(7).text());
		int assist = Integer.parseInt(team.select("tr > td").get(8).text());
		int steal = Integer.parseInt(team.select("tr > td").get(9).text());
		int block = Integer.parseInt(team.select("tr > td").get(10).text());
		int turnover = Integer.parseInt(team.select("tr > td").get(11).text());
		int foul = Integer.parseInt(team.select("tr > td").get(12).text());
		int point = Integer.parseInt(team.select("tr > td").get(13).text());
		
		int fieldGoalMade = Integer.parseInt(filedGoal[0]);
		int fieldGoalAttempt = Integer.parseInt(filedGoal[1]);
		int threePointMade = Integer.parseInt(threePoint[0]);
		int threePointAttempt = Integer.parseInt(threePoint[1]);
		int freeThrowMade = Integer.parseInt(freeThrow[0]);
		int freeThrowAttempt = Integer.parseInt(freeThrow[1]);
		
		TeamGameStat teamGameStat = TeamGameStat.builder()
				.gameNumber(gameIdInt)
				.teamName(teamName)
				.opponentTeamName(opponentTeamName)
				.home(isHome)
				.fieldGoalMade(fieldGoalMade)
				.fieldGoalAttempt(fieldGoalAttempt)
				.threePointMade(threePointMade)
				.threePointAttempt(threePointAttempt)
				.freeThrowMade(freeThrowMade)
				.freeThrowAttempt(freeThrowAttempt)
				.offensiveRebound(offensiveRebound)
				.defensiveRebound(defensiveRebound)
				.totalRebound(totalRebound)
				.assist(assist)
				.steal(steal)
				.block(block)
				.turnover(turnover)
				.foul(foul)
				.point(point)
				.overtime(isOverTime)
				.build();
		return teamGameStat;
	}
	
	boolean isOverTime(Document document) {
		boolean isOverTime = true;
		Elements ot = document.select("div.ui, .nine, .column, .grid");
		
		Element overtime = ot.select("div > div").get(7);

		String overTimeElement = overtime.select("div > font").get(5).text();
		
		String notOverTime = "OT - -";
		
		if(overTimeElement.contains(notOverTime)) {
			isOverTime = false;
		}
		
		return isOverTime;
	}
	
}
