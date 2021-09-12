package com.kihwangkwon.businesslogic.player.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.player.PlayerApi;
import com.kihwangkwon.businesslogic.player.domain.PlayerRating;
import com.kihwangkwon.businesslogic.player.domain.PlayerStat;
import com.kihwangkwon.businesslogic.team.domain.TeamTag;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;

@Service
public class PlayerHtmlParser {
	private PlayerApi playerApi;
	
	@Autowired
	PlayerHtmlParser(PlayerApi playerApi){
		this.playerApi = playerApi;
	}
	
	public List<PlayerStat> getPlayerStatFromTeamPage(TeamURLTag teamTag) {
		Document document = playerApi.getPlayerStatByTeamPage(teamTag.toString());
		List<PlayerStat> playerStatList = documentToPlayersStat(document);
		return playerStatList;
	}
	
	
	private List documentToPlayersStat(Document document) {
		List<PlayerStat> playerStatList = new ArrayList<PlayerStat>();
		
		Element mainStatTable = document.select("tbody").get(0);
		Element shootingStatTable = document.select("tbody").get(1);
		Element advancedStatTable = document.select("tbody").get(2);
		Element miscellaneousStatTable = document.select("tbody").get(3);
		
		playerStatList = mainStat(playerStatList, mainStatTable);
		playerStatList = shootingStat(playerStatList, shootingStatTable);
		playerStatList = advancedStat(playerStatList, advancedStatTable);
		playerStatList = miscellaneousStat(playerStatList, miscellaneousStatTable);
		
		return playerStatList;
	}
	
	//첫 작업이기 때문에 playerStat 생성해서 리스트에 할당하는 작업도 필요
	private List mainStat(List playerStatList, Element mainStatTable) {
		Elements eachPlayer = mainStatTable.select("tbody > tr");
		Iterator<Element> eachPlayerList = eachPlayer.iterator();
		while(eachPlayerList.hasNext()) {
			Element mainStatRaw = eachPlayerList.next();
			PlayerStat playerStat = PlayerStat.builder().build();
			playerStat = getPlayerMainStat(playerStat, mainStatRaw);
			playerStatList.add(playerStat);
		}
		return playerStatList;
	}
	
	private PlayerStat getPlayerMainStat(PlayerStat playerStat, Element mainStatRaw) {
		
		Elements stats = mainStatRaw.select("tr > td");
		
		String position = stats.get(0).text();
		
		String playerName = stats.get(1).text();
		
		String playerId = stats.get(1).select("a").attr("href");
		//.html 삭제
		playerId = playerId.substring(0,playerId.length()-5);
		
		int gamePlay = Integer.parseInt(stats.get(2).text());
		int gameStart = Integer.parseInt(stats.get(3).text());
		
		double min = Double.valueOf(stats.get(4).text());
		
		double fieldGoalMade = Double.valueOf(stats.get(5).text());
		double fieldGoalAttempt= Double.valueOf(stats.get(6).text());
		double fieldGoalPercentage= Double.valueOf(stats.get(7).text());
		
		double threePointMade= Double.valueOf(stats.get(8).text());
		double threePointAttempt= Double.valueOf(stats.get(9).text());
		double threePointPercentage= Double.valueOf(stats.get(10).text());
		
		double freeThrowMade= Double.valueOf(stats.get(11).text());
		double freeThrowAttempt= Double.valueOf(stats.get(12).text());
		double freeThrowPercentage= Double.valueOf(stats.get(13).text());
		
		double offensiveRebound = Double.valueOf(stats.get(14).text());
		double defensiveRebound = Double.valueOf(stats.get(15).text());
		double totalRebound = Double.valueOf(stats.get(16).text());
		double assist = Double.valueOf(stats.get(17).text());
		double steal = Double.valueOf(stats.get(18).text());
		double block = Double.valueOf(stats.get(19).text());
		double turnover = Double.valueOf(stats.get(20).text());
		double foul = Double.valueOf(stats.get(21).text());
		double point = Double.valueOf(stats.get(22).text());
		
		playerStat.setPosition(position);
		playerStat.setPlayerName(playerName);
		playerStat.setPlayerId(playerId);
		
		playerStat.setGamePlay(gamePlay);
		playerStat.setGameStart(gameStart);
		playerStat.setMin(min);
		
		playerStat.setFieldGoalMade(fieldGoalMade);
		playerStat.setFieldGoalAttempt(fieldGoalAttempt);
		playerStat.setFieldGoalPercentage(fieldGoalPercentage);
		
		playerStat.setThreePointMade(threePointMade);
		playerStat.setThreePointAttempt(threePointAttempt);
		playerStat.setThreePointPercentage(threePointPercentage);
		
		playerStat.setFreeThrowMade(freeThrowMade);
		playerStat.setFreeThrowAttempt(freeThrowAttempt);
		playerStat.setFreeThrowPercentage(freeThrowPercentage);
		
		playerStat.setOffensiveRebound(offensiveRebound);
		playerStat.setDefensiveRebound(defensiveRebound);
		playerStat.setTotalRebound(totalRebound);
		
		playerStat.setAssist(assist);
		playerStat.setSteal(steal);
		playerStat.setBlock(block);
		playerStat.setTurnover(turnover);
		playerStat.setFoul(foul);
		playerStat.setPoint(point);
		
		return playerStat;
	}
	
	private List shootingStat(List<PlayerStat> playerStatList, Element shootingStatTable) {
		Elements eachPlayer = shootingStatTable.select("tbody > tr");
		Iterator<Element> eachPlayerList = eachPlayer.iterator();
		int i = 0;
		while(eachPlayerList.hasNext()) {
			PlayerStat playerStat = playerStatList.get(i);
			playerStat = getPlayerShootingStat(playerStat,eachPlayerList.next());
			i++;
		}
		return playerStatList;
	}
	
	private PlayerStat getPlayerShootingStat(PlayerStat playerStat, Element shootingStatRaw) {
		Elements stats = shootingStatRaw.select("tr > td");
		
		double twoPointRatio = Double.valueOf(stats.get(2).text());
		
		double twoPointSuccessRate = 0;
		
		String twoPointSuccessRateString = stats.get(8).text();
		
		if(!twoPointSuccessRateString.equals("NaN")) {
			twoPointSuccessRate = Double.valueOf(stats.get(8).text());
		}
		
		double twoPointRatioZeroToThree = Double.valueOf(stats.get(3).text());
		double twoPointSuccessRateZeroToThree = Double.valueOf(stats.get(9).text());
		
		double twoPointRatioThreeToTen = Double.valueOf(stats.get(4).text());
		double twoPointSuccessRateThreeToTen= Double.valueOf(stats.get(10).text());
		
		double twoPointRatioTenToSixteen= Double.valueOf(stats.get(5).text());
		double twoPointSuccessRateTenToSixteen= Double.valueOf(stats.get(11).text());
		
		double twoPointRatioSixteenToThreePoint= Double.valueOf(stats.get(6).text());
		double twoPointSuccessRateSixteenToThreePoint= Double.valueOf(stats.get(12).text());
		
		double threePointRatio= Double.valueOf(stats.get(7).text());
		double threePointSuccessRate= Double.valueOf(stats.get(13).text());
		
		playerStat.setTwoPointRatio(twoPointRatio);
		playerStat.setTwoPointSuccessRate(twoPointSuccessRate);
		
		playerStat.setTwoPointRatioZeroToThree(twoPointRatioZeroToThree);
		playerStat.setTwoPointSuccessRateZeroToThree(twoPointSuccessRateZeroToThree);
		
		playerStat.setTwoPointRatioThreeToTen(twoPointRatioThreeToTen);
		playerStat.setTwoPointSuccessRateThreeToTen(twoPointSuccessRateThreeToTen);
		
		playerStat.setTwoPointRatioTenToSixteen(twoPointRatioTenToSixteen);
		playerStat.setTwoPointSuccessRateTenToSixteen(twoPointSuccessRateTenToSixteen);
		
		playerStat.setTwoPointRatioSixteenToThreePoint(twoPointRatioSixteenToThreePoint);
		playerStat.setTwoPointSuccessRateSixteenToThreePoint(twoPointSuccessRateSixteenToThreePoint);
		
		playerStat.setThreePointRatio(threePointRatio);
		playerStat.setThreePointSuccessRate(threePointSuccessRate);
		
		return playerStat;
	}
	
	private List advancedStat(List<PlayerStat> playerStatList, Element advancedStatTable) {
		
		Elements eachPlayer = advancedStatTable.select("tbody > tr");
		Iterator<Element> eachPlayerList = eachPlayer.iterator();
		int i = 0;
		while(eachPlayerList.hasNext()) {
			PlayerStat playerStat = playerStatList.get(i);
			playerStat = getPlayerAdvancedStat(playerStat, eachPlayerList.next());
			i++;
		}
		return playerStatList;
	}
	
	private PlayerStat getPlayerAdvancedStat(PlayerStat playerStat, Element advancedStatRaw) {
		Elements stats = advancedStatRaw.select("tr > td");

		double onCourtMargin = getDoubleFromText(stats.get(2).text());
		double offCourtMargin = getDoubleFromText(stats.get(3).text());
		double netMargin = getDoubleFromText(stats.get(4).text());
		
		double per = getDoubleFromText(stats.get(5).text());
		
		double trueShootingPercentage = getDoubleFromText(stats.get(6).text());
		double effectiveFieldGoalPercentage = getDoubleFromText(stats.get(7).text());

		double offensiveReboundPercentage = getDoubleFromText(stats.get(8).text());
		double defensiveReboundPercentage = getDoubleFromText(stats.get(9).text());
		double totalReboundPercentage = getDoubleFromText(stats.get(10).text());;
		double assistPercentage = getDoubleFromText(stats.get(11).text());
		double stealPercentage = getDoubleFromText(stats.get(12).text());
		double blockPercentage = getDoubleFromText(stats.get(13).text());

		double turnoverPercentage = getDoubleFromText(stats.get(14).text());
		double assistTurnoverRatio = getDoubleFromText(stats.get(15).text());
		double usagePercentage = getDoubleFromText(stats.get(16).text());
		
		playerStat.setOnCourtMargin(onCourtMargin);
		playerStat.setOffCourtMargin(offCourtMargin);
		playerStat.setNetMargin(netMargin);
		playerStat.setPer(per);
		playerStat.setTrueShootingPercentage(trueShootingPercentage);
		playerStat.setEffectiveFieldGoalPercentage(effectiveFieldGoalPercentage);
		playerStat.setOffensiveReboundPercentage(offensiveReboundPercentage);
		playerStat.setDefensiveReboundPercentage(defensiveReboundPercentage);
		playerStat.setTotalReboundPercentage(totalReboundPercentage);
		playerStat.setAssistPercentage(assistPercentage);
		playerStat.setStealPercentage(stealPercentage);
		playerStat.setBlockPercentage(blockPercentage);
		playerStat.setTurnoverPercentage(turnoverPercentage);
		playerStat.setAssistTurnoverRatio(assistTurnoverRatio);
		playerStat.setUsagePercentage(usagePercentage);
		
		return playerStat;
	}
	
	private List miscellaneousStat(List<PlayerStat> playerStatList, Element miscellaneousStatTable) {
		Elements eachPlayer = miscellaneousStatTable.select("tbody > tr");
		Iterator<Element> eachPlayerList = eachPlayer.iterator();
		int i = 0;
		while(eachPlayerList.hasNext()) {
			PlayerStat playerStat = playerStatList.get(i);
			playerStat = getPlayerMiscellaneousStat(playerStat, eachPlayerList.next());
			i++;
		}
		return playerStatList;
	}
	
	
	private PlayerStat getPlayerMiscellaneousStat(PlayerStat playerStat, Element miscellaneousStat) {
		Elements stats = miscellaneousStat.select("tr > td");
		
		 double drivesStopped = getDoubleFromText(stats.get(2).text());
		 double drivesFaced = getDoubleFromText(stats.get(3).text());
		 double drivesStoppedPercentage = getDoubleFromText(stats.get(4).text());
		 double turnoversForced = getDoubleFromText(stats.get(5).text());
		 double pointsAllowed = getDoubleFromText(stats.get(6).text());
		 double shotsFaced = getDoubleFromText(stats.get(7).text());
		 double pointsAllowedPerShotsFaced = getDoubleFromText(stats.get(8).text());
		 double touches = getDoubleFromText(stats.get(9).text());
		 double turnoversPerTouches = getDoubleFromText(stats.get(10).text());
		 double assistsPerTouches = getDoubleFromText(stats.get(11).text());
		 double chargesTaken = getDoubleFromText(stats.get(12).text());
		 double technicalFouls = getDoubleFromText(stats.get(13).text());
		
		 playerStat.setDrivesStopped(drivesStopped);
		 playerStat.setDrivesFaced(drivesFaced);
		 playerStat.setDrivesStoppedPercentage(drivesStoppedPercentage);
		 
		 playerStat.setTurnoversForced(turnoversForced);
		 playerStat.setPointsAllowed(pointsAllowed);
		 playerStat.setShotsFaced(shotsFaced);
		 
		 playerStat.setPointsAllowedPerShotsFaced(pointsAllowedPerShotsFaced);
		 playerStat.setTouches(touches);
		 playerStat.setTurnoversPerTouches(turnoversPerTouches);
		 
		 playerStat.setAssistsPerTouches(assistsPerTouches);
		 playerStat.setChargesTaken(chargesTaken);
		 playerStat.setTechnicalFouls(technicalFouls);

		 return playerStat;
	}
	
	private Double getDoubleFromText(String text) {
		double result = 0 ;
		
		if(!text.equals("NaN") && !text.equals("N/A")) {
			result = Double.valueOf(text);
		}
		
		return result;

	}
	
	public List<PlayerRating> getPlayerRatingList(TeamURLTag teamTag){
		Document teamRating = playerApi.getPlayerRatingFromTeamPage(teamTag.toString());
		return getPlayerRatingListFromDocument(teamRating);
	};
	
	private List<PlayerRating> getPlayerRatingListFromDocument(Document document){
		List<PlayerRating> playerRatingList = new ArrayList<PlayerRating>();
		
		Iterator<Element> playerItertor = document.select("tbody").get(0).select("tr").iterator();
		Iterator<Element> salaryItertor = document.select("tbody").get(1).select("tr").iterator();
		while(playerItertor.hasNext()) {
			Element playerRatingRaw = playerItertor.next();
			Element playerSalaryRaw = salaryItertor.next();
			PlayerRating playerRating = getPlayerRating(playerRatingRaw,playerSalaryRaw);
			playerRatingList.add(playerRating);
		}
		
		return playerRatingList;
	}
	
	private PlayerRating getPlayerRating(Element element, Element salaryElement) {
		
		Elements playerRatingEach = element.select("tr > td");
		String playerId = playerRatingEach.get(2).select("a").attr("href");
	    //뒤에 .html 제거
	    playerId = playerId.substring(0,playerId.length()-5); 

	    int number = Integer.parseInt(playerRatingEach.get(0).text()); 
	    String position = playerRatingEach.get(1).text();
	    String playerName = playerRatingEach.get(2).text();
	    int fieldGoalInside = Integer.parseInt(playerRatingEach.get(3).text());
	    int fieldGoalOutside = Integer.parseInt(playerRatingEach.get(4).text());
	    int freethrow = Integer.parseInt(playerRatingEach.get(5).text());
	    int scoring = Integer.parseInt(playerRatingEach.get(6).text());
	    int pass = Integer.parseInt(playerRatingEach.get(7).text());
	    int handling = Integer.parseInt(playerRatingEach.get(8).text());
	    int offensiveRebound = Integer.parseInt(playerRatingEach.get(9).text());
	    int defensiveRebound = Integer.parseInt(playerRatingEach.get(10).text());
	    int defence = Integer.parseInt(playerRatingEach.get(11).text());
	    int block = Integer.parseInt(playerRatingEach.get(12).text());
	    int steal = Integer.parseInt(playerRatingEach.get(13).text());
	    int drawingFouls = Integer.parseInt(playerRatingEach.get(14).text());
	    int discipline = Integer.parseInt(playerRatingEach.get(15).text());
	    int iq = Integer.parseInt(playerRatingEach.get(16).text());
	    int energy = Integer.parseInt(playerRatingEach.get(17).text());
	    double overall = starCounter(playerRatingEach.get(18));
	    double potential = starCounter(playerRatingEach.get(19));

		Elements salaryElements = salaryElement.select("tr > td");
		int salary = Integer.parseInt(salaryElements.get(3).attr("data-sort"));
	    
		PlayerRating playerRating = PlayerRating.builder()
				 .playerId(playerId)
				 .number(number).position(position)
				 .playerName(playerName)
				 .fieldGoalInside(fieldGoalInside)
				 .fieldGoalOutside(fieldGoalOutside)
				 .freethrow(freethrow)
				 .scoring(scoring)
				 .pass(pass)
				 .handling(handling)
				 .offensiveRebound(offensiveRebound)
				 .defensiveRebound(defensiveRebound)
				 .defence(defence)
				 .block(block)
				 .steal(steal)
				 .drawingFouls(drawingFouls)
				 .discipline(discipline)
				 .iq(iq)
				 .energy(energy)
				 .overall(overall)
				 .potential(potential)
				 .salary(salary)
				 .build();

		 return playerRating;
	}
	
	private double starCounter(Element element) {
		double count = 0;
		Iterator<Element> starIterator = element.select("i").iterator();
		while(starIterator.hasNext()) {
			String starClass = starIterator.next().attr("class");
			
			//별 반개면 0.5 추가
			if(starClass.contains("half")) {
				count += 0.5;
			}
			// 그 외 1 추가
			else {
				count ++;
			}
		}
		return count;
	}
	
}
