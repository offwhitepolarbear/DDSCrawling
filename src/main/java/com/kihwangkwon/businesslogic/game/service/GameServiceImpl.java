package com.kihwangkwon.businesslogic.game.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.game.GameApi;
import com.kihwangkwon.businesslogic.game.domain.GameId;
import com.kihwangkwon.businesslogic.game.domain.PlayerGameStat;
import com.kihwangkwon.businesslogic.game.domain.TeamGameStat;
import com.kihwangkwon.businesslogic.game.domain.TeamStat;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;
@Service
public class GameServiceImpl implements GameService {

	private GameApi gameApi;
	
	private PlayerGameStatRepository playerGameStatRepository;
	private TeamGameStatRepository teamGameStatRepository;
	private GameIdRepository gameIdRepository;
	
	@Autowired
	public GameServiceImpl(GameApi gameApi, PlayerGameStatRepository playerGameStatRepository, TeamGameStatRepository teamGameStatRepository, GameIdRepository gameIdRepository) {
		this.gameApi = gameApi;
		this.playerGameStatRepository = playerGameStatRepository;
		this.teamGameStatRepository = teamGameStatRepository;
		this.gameIdRepository = gameIdRepository;
	}
	
	@Override
	public void getGame(String gameId) {
		Document doc = gameApi.getGameInfo(gameId);
		String awayName = doc.select("div > font[face=roboto]").get(0).text();
		String homeName = doc.select("div > font[face=roboto]").get(1).text();
		
		Elements awayStarter = doc.select("table.ui, .basic, .selectable, .table").get(0).select("tbody > tr[align=right]");
		Elements awayBench = doc.select("table.ui, .basic, .selectable, .table").get(1).select("tbody > tr[align=right]").not("tr[style=font-weight:bold]");
		
		Elements awayTotal = doc.select("table.ui, .basic, .selectable, .table").get(1).select("tbody > tr[style=font-weight:bold]");
		
		Elements homeStarter = doc.select("table.ui, .basic, .selectable, .table").get(2).select("tbody > tr[align=right]");
		Elements homeBench = doc.select("table.ui, .basic, .selectable, .table").get(3).select("tbody > tr[align=right]").not("tr[style=font-weight:bold]");
		
		Elements homeTotal = doc.select("table.ui, .basic, .selectable, .table").get(3).select("tbody > tr[style=font-weight:bold]");
		// 원정 스타터
		List<PlayerGameStat> awayStaterList = playersStatConvert(awayStarter, gameId, awayName, homeName, false, true);
		// 원정 벤치 
		List<PlayerGameStat> awayBenchList = playersStatConvert(awayBench, gameId, awayName, homeName, false, false);
		// 홈 스타터
		List<PlayerGameStat> homeStaterList = playersStatConvert(homeStarter, gameId, homeName, awayName, true, true);
		// 홈 벤치
		List<PlayerGameStat> homeBenchList = playersStatConvert(homeBench, gameId, homeName, awayName, true, false);
		
		// 원정 팀
		TeamGameStat awayTeamStat = teamStatConvertor(awayTotal.get(0), gameId, awayName, homeName, false);
		// 홈 팀
		TeamGameStat homeTeamStat = teamStatConvertor(homeTotal.get(0), gameId, homeName, awayName, true);
		
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
		
		System.out.println(homeTeam);
		System.out.println(awayTeam);
		
		saveTeamStat(homeTeam);
		saveTeamStat(awayTeam);
	
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
	
	private void saveTeamStat(TeamStat teamStat) {
		//팀 스탯 저장
		teamGameStatRepository.save(teamStat.getTeamGameStat());
		
		//돌면서 플레이어 스탯 저장
		for(PlayerGameStat playerGameStat : teamStat.getPlayerGameStatList()) {
			playerGameStatRepository.save(playerGameStat);
		}
		
	}
	
	private List<PlayerGameStat> playersStatConvert(Elements players, String gameId, String teamName, String opponentTeamName, boolean isHome, boolean isStarter) {
		List playerGameStatList = new ArrayList<>();
		Iterator<Element> player = players.iterator();
		while(player.hasNext()) {
			PlayerGameStat playerGameStat = playerGameStatConvertor(player.next(), gameId, teamName, opponentTeamName, isHome, isStarter);
			playerGameStatList.add(playerGameStat);
		}
		return playerGameStatList;
	}

	private PlayerGameStat playerGameStatConvertor(Element player, String gameId, String teamName, String opponentTeamName, boolean isHome, boolean isStarter) {
		
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
			.build();
		return playerGameStat;
	}
	
	private TeamGameStat teamStatConvertor(Element team, String gameId, String teamName, String opponentTeamName, boolean isHome) {
		
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
				.build();
		return teamGameStat;
	}

	@Override
	public void getGameIds() {
		TeamURLTag[] all = TeamURLTag.values();

		for(TeamURLTag teamTag:all) {
			String teamName = teamTag.toString();
			Document document = gameApi.getGameList(teamName);
			List idList = getGameIdList(document);
			saveGameIds(idList);
		}
	}

	private List<Integer> getGameIdList(Document document){
		List<Integer> idList = new ArrayList<Integer>();
		Iterator<Element> gameIds= document.select("td[data-label=Result] > a").iterator();
		while(gameIds.hasNext()) {
			String gameId = gameIds.next().attr("href");
			gameId = gameId.replace("Game","");
			gameId = gameId.replace(".html","");
			idList.add(Integer.parseInt(gameId));
		}
		return idList;
	}
	
	private void saveGameIds(List<Integer> idList){
		for(int id: idList) {
			GameId gameId = new GameId();
			gameId.setGameId(id);
			gameIdRepository.save(gameId);
		}
		
	}
	
	private List selectAllGameId() {
		return gameIdRepository.findAll();
	}

	@Override
	public void getAllGameData() {
		List<GameId> gameIdList = selectAllGameId();
		for(GameId gameId : gameIdList) {
			String gameIdString = String.valueOf(gameId.getGameId());
			getGame(gameIdString);
		}
	}
}
