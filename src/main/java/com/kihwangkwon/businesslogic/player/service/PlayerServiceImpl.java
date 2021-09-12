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
import com.kihwangkwon.businesslogic.player.domain.PlayerId;
import com.kihwangkwon.businesslogic.player.domain.PlayerRating;
import com.kihwangkwon.businesslogic.player.domain.PlayerStat;
import com.kihwangkwon.businesslogic.player.domain.PlayerStatWeekly;
import com.kihwangkwon.businesslogic.player.repository.PlayerIdRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerRatingRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerStatRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerStatWeeklyRepository;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;

@Service
public class PlayerServiceImpl implements PlayerService {

	private PlayerApi playerApi;

	private PlayerHtmlParser playerHtmlParser;
	
	private PlayerIdRepository playerRepository;
	private PlayerStatRepository playerStatRepository;
	private PlayerRatingRepository playerRatingRepository;
	private PlayerStatWeeklyRepository playerStatWeeklyRepository;
	
	@Autowired
	public PlayerServiceImpl(PlayerApi playerApi
			, PlayerIdRepository playerRepository
			, PlayerStatRepository playerStatRepository
			, PlayerRatingRepository playerRatingRepository
			, PlayerStatWeeklyRepository playerStatWeeklyRepository
			, PlayerHtmlParser playerHtmlParser) {
		this.playerApi = playerApi;
		this.playerRepository = playerRepository;
		this.playerStatRepository = playerStatRepository;
		this.playerRatingRepository = playerRatingRepository;
		this.playerStatWeeklyRepository = playerStatWeeklyRepository;
		this.playerHtmlParser = playerHtmlParser;
	}
	
	@Override
	public void getPlayerIndex() {
		Document document = playerApi.getPlayerIndex();
		
		
		Iterator<Element> nameIterator = document.select("td > a").not("a[style=text-decoration: none]").iterator();
		
		while(nameIterator.hasNext()) {
			Element playerName = nameIterator.next();
			boolean emptyCheck = playerName.text().equals("");
			boolean weiredHtmlCheck = playerName.text().startsWith("http:");
			if(!emptyCheck && !weiredHtmlCheck) {
				PlayerId playerId= elementToPlayerId(playerName);
				playerRepository.save(playerId);
			}
		}
	}

	private PlayerId elementToPlayerId(Element element) {
		//이름 파싱
		//,로 스플릿
		String[] splitName = element.text().split("\\,");
		// 성 이름 순서 바꿔서 등록
		String playerName = splitName[1].replaceAll(" ","")+" "+splitName[0].replaceAll(" ","");
		
		
		//선수id 파싱
		String htmlId = element.attr("href");
		//id 뒤에 .html 삭제
		//htmlId = htmlId.substring(0,htmlId.length()-5);
		htmlId = htmlId.replace(".html", "");
		
		PlayerId playerId = PlayerId.builder().playerName(playerName).playerId(htmlId).build();
		return playerId;
	}
	
	
	
	@Override
	public void getPlayerStatFromTeam() {
		
		
		//전체 팀 태그 가져오기
		TeamURLTag[] teamUrls = TeamURLTag.values();
		
		// 팀별로 반복
		for(TeamURLTag teamTag:teamUrls) {;
			List<PlayerStat> playerStatList = playerHtmlParser.getPlayerStatFromTeamPage(teamTag);
			
			//팀별 가져온 스탯 저장
			savePlayerStatFromTeamPage(playerStatList);
			
		}
	}
	
	private void savePlayerStatFromTeamPage(List<PlayerStat> playerStatList) {
		for(PlayerStat playerStat : playerStatList) {
			playerStatRepository.save(playerStat);
		}
	}
	

	@Override
	public void getPlayerRatingListFromTeam() {
		for(TeamURLTag teamTag: TeamURLTag.values()) {
			List<PlayerRating> playerRatingList = playerHtmlParser.getPlayerRatingList(teamTag);
			savePlayerRatingListTeam(playerRatingList);
		}
	}
	
	private void savePlayerRatingListTeam(List<PlayerRating> playerRatingList) {
		for (PlayerRating playerRating : playerRatingList) {
			playerRatingRepository.save(playerRating);
		}
	}
	
	public void savePlayerStatWeeklyAll(int lastWeekVersion, int thisWeekVersion, int week) {
		
		List lastWeekStats = playerStatRepository.findByVersion(lastWeekVersion);
		List thisWeekStats = playerStatRepository.findByVersion(thisWeekVersion);
		System.out.println(lastWeekStats.size());
		System.out.println(thisWeekStats.size());
		findSamePlayerAndSave(lastWeekStats, thisWeekStats, week);
		
	}
	
	private void findSamePlayerAndSave(List<PlayerStat> lastWeekStats, List<PlayerStat> thisWeekStats, int week) {
		for(PlayerStat lastWeekPlayerStat:lastWeekStats) {
			for(int i=0;i<thisWeekStats.size() ;i++) {
				String lastWeekPlayerId = lastWeekPlayerStat.getPlayerId();
				String thisWeekPlayerId = thisWeekStats.get(i).getPlayerId();
				int lastWeekGamePlay = lastWeekPlayerStat.getGamePlay();
				int thisWeekGamePlay = thisWeekStats.get(i).getGamePlay();
				if(lastWeekPlayerId.equals(thisWeekPlayerId)) {
					if (thisWeekGamePlay>lastWeekGamePlay) {
						//같은 거 찾음
						PlayerStatWeekly playerStatWeekly = getPlayerStatWeekly(lastWeekPlayerStat, thisWeekStats.get(i));
						playerStatWeekly.setWeek(week);
						playerStatWeeklyRepository.save(playerStatWeekly);
						
						break;
					}
					else {
						//경기 참가 없음
						//지난주거 그대로 저장
					}
					
				}
					
				//thisWeekStats.remove(i);
				//break;
			}
		}
	}
	
	
	public PlayerStatWeekly getPlayerStatWeekly(PlayerStat lastWeekPlayerStat, PlayerStat thisWeekPlayerStat) {

			String playerId = lastWeekPlayerStat.getPlayerId();
			String playerName = lastWeekPlayerStat.getPlayerName();
			String position = lastWeekPlayerStat.getPosition();
			int version = lastWeekPlayerStat.getVersion();
			int lastWeekGamePlay = lastWeekPlayerStat.getGamePlay();
			int thisWeekGamePlay = thisWeekPlayerStat.getGamePlay();
			
			int gamePlay = thisWeekPlayerStat.getGamePlay()-lastWeekPlayerStat.getGamePlay();
			int gameStart = thisWeekPlayerStat.getGameStart()-lastWeekPlayerStat.getGameStart();
			double min =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getMin(),thisWeekPlayerStat.getMin()) ;
			
			double fieldGoalMade =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFieldGoalMade(),thisWeekPlayerStat.getFieldGoalMade());
			double fieldGoalAttempt = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFieldGoalAttempt(),thisWeekPlayerStat.getFieldGoalAttempt());
			
			double fieldGoalPercentage =  weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFieldGoalPercentage(),thisWeekPlayerStat.getFieldGoalPercentage());
			
			double threePointMade =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getThreePointMade(),thisWeekPlayerStat.getThreePointMade());
			double threePointAttempt =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getThreePointAttempt(),thisWeekPlayerStat.getThreePointAttempt());
			
			double threePointPercentage =  weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getThreePointPercentage(),thisWeekPlayerStat.getThreePointPercentage());
			
			double freeThrowMade =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFreeThrowMade(),thisWeekPlayerStat.getFreeThrowMade());
			double freeThrowAttempt =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFreeThrowAttempt(),thisWeekPlayerStat.getFreeThrowAttempt());
			
			double freeThrowPercentage =  weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFreeThrowPercentage(),thisWeekPlayerStat.getFreeThrowPercentage());
			
			double offensiveRebound =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getOffensiveRebound(),thisWeekPlayerStat.getOffensiveRebound());
			double defensiveRebound =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getDefensiveRebound(),thisWeekPlayerStat.getDefensiveRebound());
			double totalRebound =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTotalRebound(),thisWeekPlayerStat.getTotalRebound());
			double assist =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getAssist(),thisWeekPlayerStat.getAssist());
			double steal = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getSteal(),thisWeekPlayerStat.getSteal());
			double block =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getBlock(),thisWeekPlayerStat.getBlock());
			double turnover =  weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTurnover(),thisWeekPlayerStat.getTurnover());
			double foul = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getFoul(),thisWeekPlayerStat.getFoul());
			double point = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getPoint(),thisWeekPlayerStat.getPoint());
			
			double twoPointRatio = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointRatio(),thisWeekPlayerStat.getTwoPointRatio());
			double twoPointSuccessRate = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointSuccessRate(),thisWeekPlayerStat.getTwoPointSuccessRate());
			
			double twoPointRatioZeroToThree = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointRatioZeroToThree(),thisWeekPlayerStat.getTwoPointRatioZeroToThree());
			double twoPointSuccessRateZeroToThree = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointSuccessRateZeroToThree(),thisWeekPlayerStat.getTwoPointSuccessRateZeroToThree());
			
			double twoPointRatioThreeToTen = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointRatioThreeToTen(),thisWeekPlayerStat.getTwoPointRatioThreeToTen());
			double twoPointSuccessRateThreeToTen = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointSuccessRateThreeToTen(),thisWeekPlayerStat.getTwoPointSuccessRateThreeToTen());
			
			double twoPointRatioTenToSixteen = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointRatioTenToSixteen(),thisWeekPlayerStat.getTwoPointRatioTenToSixteen());
			double twoPointSuccessRateTenToSixteen = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointSuccessRateTenToSixteen(),thisWeekPlayerStat.getTwoPointSuccessRateTenToSixteen());
			
			double twoPointRatioSixteenToThreePoint = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointRatioSixteenToThreePoint(),thisWeekPlayerStat.getTwoPointRatioSixteenToThreePoint());
			double twoPointSuccessRateSixteenToThreePoint = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTwoPointSuccessRateSixteenToThreePoint(),thisWeekPlayerStat.getTwoPointSuccessRateSixteenToThreePoint());
			
			double threePointRatio = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getThreePointRatio(),thisWeekPlayerStat.getThreePointRatio());
			double threePointSuccessRate = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getThreePointSuccessRate(),thisWeekPlayerStat.getThreePointSuccessRate());
			
			double onCourtMargin = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getOnCourtMargin(),thisWeekPlayerStat.getOnCourtMargin());
			double offCourtMargin = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getOffCourtMargin(),thisWeekPlayerStat.getOffCourtMargin());
			double netMargin = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getNetMargin(),thisWeekPlayerStat.getNetMargin());

			double per = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getPer(),thisWeekPlayerStat.getPer());
			double trueShootingPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTrueShootingPercentage(),thisWeekPlayerStat.getTrueShootingPercentage());
			double effectiveFieldGoalPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getEffectiveFieldGoalPercentage(),thisWeekPlayerStat.getEffectiveFieldGoalPercentage());
			
			double offensiveReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getOffensiveReboundPercentage(),thisWeekPlayerStat.getOffensiveReboundPercentage());
			double defensiveReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getDefensiveReboundPercentage(),thisWeekPlayerStat.getDefensiveReboundPercentage());
			double totalReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTotalReboundPercentage(),thisWeekPlayerStat.getTotalReboundPercentage());
			double assistPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getAssistPercentage(),thisWeekPlayerStat.getAssistPercentage());
			double stealPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getStealPercentage(),thisWeekPlayerStat.getStealPercentage());
			double blockPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getBlockPercentage(),thisWeekPlayerStat.getBlockPercentage());

			double turnoverPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTurnoverPercentage(),thisWeekPlayerStat.getTurnoverPercentage());
			double assistTurnoverRatio = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getAssistTurnoverRatio(),thisWeekPlayerStat.getAssistTurnoverRatio());
			double usagePercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getUsagePercentage(),thisWeekPlayerStat.getUsagePercentage());
			
			double drivesStopped = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getDrivesStopped(),thisWeekPlayerStat.getDrivesStopped());
			double drivesFaced = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getDrivesFaced(),thisWeekPlayerStat.getDrivesFaced());
			double drivesStoppedPercentage = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getDrivesStoppedPercentage(),thisWeekPlayerStat.getDrivesStoppedPercentage());
			double turnoversForced = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTurnoversForced(),thisWeekPlayerStat.getTurnoversForced());
			double pointsAllowed = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getPointsAllowed(),thisWeekPlayerStat.getPointsAllowed());
			double shotsFaced = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getShotsFaced(),thisWeekPlayerStat.getShotsFaced());
			double pointsAllowedPerShotsFaced = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getPointsAllowedPerShotsFaced(),thisWeekPlayerStat.getPointsAllowedPerShotsFaced());
			double touches = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTouches(),thisWeekPlayerStat.getTouches());
			double turnoversPerTouches = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTurnoversPerTouches(),thisWeekPlayerStat.getTurnoversPerTouches());
			double assistsPerTouches = weeklyAdjustInThree(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getAssistsPerTouches(),thisWeekPlayerStat.getAssistsPerTouches());
			double chargesTaken = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getChargesTaken(),thisWeekPlayerStat.getChargesTaken());
			double technicalFouls = weeklyAdjustInOne(lastWeekGamePlay,thisWeekGamePlay,lastWeekPlayerStat.getTechnicalFouls(),thisWeekPlayerStat.getTechnicalFouls());
			
	
		PlayerStatWeekly playerStatWeekly = PlayerStatWeekly.builder()
				.assist(assist)
				.assistPercentage(assistPercentage)
				.assistsPerTouches(assistsPerTouches)
				.assistTurnoverRatio(assistTurnoverRatio)
				.block(block).blockPercentage(blockPercentage)
				.chargesTaken(chargesTaken)
				.defensiveRebound(defensiveRebound).defensiveReboundPercentage(defensiveReboundPercentage)
				.drivesFaced(drivesFaced).drivesStopped(drivesStopped).drivesStoppedPercentage(drivesStoppedPercentage)
				.effectiveFieldGoalPercentage(effectiveFieldGoalPercentage).fieldGoalAttempt(fieldGoalAttempt)
				.fieldGoalMade(fieldGoalMade)
				.fieldGoalPercentage(fieldGoalPercentage)
				.foul(foul).freeThrowAttempt(freeThrowAttempt).freeThrowMade(freeThrowMade).freeThrowPercentage(freeThrowPercentage).gamePlay(gamePlay).gameStart(gameStart).min(min).netMargin(netMargin).offCourtMargin(offCourtMargin)
				.offensiveRebound(offensiveRebound).offensiveReboundPercentage(offensiveReboundPercentage)
				.onCourtMargin(onCourtMargin)
				.per(per)
				.playerId(playerId).playerName(playerName)
				.point(point)
				.pointsAllowed(pointsAllowed)
				.pointsAllowedPerShotsFaced(pointsAllowedPerShotsFaced).position(position)
				.shotsFaced(shotsFaced)
				.steal(steal).stealPercentage(stealPercentage)
				.technicalFouls(technicalFouls)
				.threePointAttempt(threePointAttempt)
				.threePointMade(threePointMade)
				.threePointPercentage(threePointPercentage)
				.threePointRatio(threePointRatio)
				.threePointSuccessRate(threePointSuccessRate)
				.totalRebound(totalRebound)
				.totalReboundPercentage(totalReboundPercentage)
				.touches(touches)
				.trueShootingPercentage(trueShootingPercentage)
				.turnover(turnover)
				.turnoverPercentage(turnoverPercentage)
				.turnoversForced(turnoversForced)
				.turnoversPerTouches(turnoversPerTouches)
				.twoPointRatio(twoPointRatio)
				.twoPointRatioSixteenToThreePoint(twoPointRatioSixteenToThreePoint)
				.twoPointRatioTenToSixteen(twoPointRatioTenToSixteen)
				.twoPointRatioThreeToTen(twoPointRatioThreeToTen)
				.twoPointRatioZeroToThree(twoPointRatioZeroToThree)
				.twoPointSuccessRate(twoPointSuccessRate)
				.twoPointSuccessRateSixteenToThreePoint(twoPointSuccessRateSixteenToThreePoint)
				.twoPointSuccessRateTenToSixteen(twoPointSuccessRateTenToSixteen)
				.twoPointSuccessRateThreeToTen(twoPointSuccessRateThreeToTen)
				.twoPointSuccessRateZeroToThree(twoPointSuccessRateZeroToThree)
				.usagePercentage(usagePercentage)
				.version(version)
				.build();
		return playerStatWeekly;
	}

	private double weeklyAdjustInOne(int lastWeekGamePlay,int thisWeekGamePlay, double lastWeekResult, double thisWeekResult) {
		double result = (thisWeekResult*thisWeekGamePlay-lastWeekResult*lastWeekGamePlay)/(thisWeekGamePlay-lastWeekGamePlay);

		//자리수 조절
		result = Math.round(result*10)/10.0; 

		return result;
	}
	
	
	private double weeklyAdjustInThree(int lastWeekGamePlay,int thisWeekGamePlay, double lastWeekResult, double thisWeekResult) {
		double result = (thisWeekResult*thisWeekGamePlay-lastWeekResult*lastWeekGamePlay)/(thisWeekGamePlay-lastWeekGamePlay);

		//자리수 조절
		result = Math.round(result*1000)/1000.0; 

		return result;
	}
	
	private double shootRatio(int lastWeekGamePlay,int thisWeekGamePlay, double lastWeekResult, double thisWeekResult) {
		double result = (thisWeekResult*thisWeekGamePlay-lastWeekResult*lastWeekGamePlay)/(thisWeekGamePlay-lastWeekGamePlay);
		return result;
	}
}
