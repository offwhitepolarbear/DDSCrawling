package com.kihwangkwon.businesslogic.player.service;

import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.player.domain.PlayerStat;
import com.kihwangkwon.businesslogic.player.domain.PlayerStatWeekly;

@Service
public class PlayerStatWeeklyParser {
	public PlayerStatWeekly getPlayerStatWeekly(PlayerStat lastWeekPlayerStat, PlayerStat thisWeekPlayerStat) {

		String playerId = lastWeekPlayerStat.getPlayerId();
		String playerName = lastWeekPlayerStat.getPlayerName();
		String position = lastWeekPlayerStat.getPosition();
		int version = lastWeekPlayerStat.getVersion();
		int lastWeekGamePlay = lastWeekPlayerStat.getGamePlay();
		int thisWeekGamePlay = thisWeekPlayerStat.getGamePlay();

		int gamePlay = thisWeekPlayerStat.getGamePlay() - lastWeekPlayerStat.getGamePlay();
		int gameStart = thisWeekPlayerStat.getGameStart() - lastWeekPlayerStat.getGameStart();
		double min = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getMin(),
				thisWeekPlayerStat.getMin());

		double fieldGoalMade = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFieldGoalMade(), thisWeekPlayerStat.getFieldGoalMade());
		double fieldGoalAttempt = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFieldGoalAttempt(), thisWeekPlayerStat.getFieldGoalAttempt());

		double fieldGoalPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFieldGoalPercentage(), thisWeekPlayerStat.getFieldGoalPercentage());

		double threePointMade = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getThreePointMade(), thisWeekPlayerStat.getThreePointMade());
		double threePointAttempt = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getThreePointAttempt(), thisWeekPlayerStat.getThreePointAttempt());

		double threePointPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getThreePointPercentage(), thisWeekPlayerStat.getThreePointPercentage());

		double freeThrowMade = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFreeThrowMade(), thisWeekPlayerStat.getFreeThrowMade());
		double freeThrowAttempt = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFreeThrowAttempt(), thisWeekPlayerStat.getFreeThrowAttempt());

		double freeThrowPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getFreeThrowPercentage(), thisWeekPlayerStat.getFreeThrowPercentage());

		double offensiveRebound = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getOffensiveRebound(), thisWeekPlayerStat.getOffensiveRebound());
		double defensiveRebound = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getDefensiveRebound(), thisWeekPlayerStat.getDefensiveRebound());
		double totalRebound = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTotalRebound(), thisWeekPlayerStat.getTotalRebound());
		
		double assist = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getAssist(),
				thisWeekPlayerStat.getAssist());
		double steal = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getSteal(),
				thisWeekPlayerStat.getSteal());
		double block = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getBlock(),
				thisWeekPlayerStat.getBlock());
		double turnover = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTurnover(), thisWeekPlayerStat.getTurnover());
		double foul = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getFoul(),
				thisWeekPlayerStat.getFoul());
		double point = calculateVolumeByGameCount(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getPoint(),
				thisWeekPlayerStat.getPoint());

		double twoPointRatio = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointRatio(), thisWeekPlayerStat.getTwoPointRatio());
		double twoPointSuccessRate = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointSuccessRate(), thisWeekPlayerStat.getTwoPointSuccessRate());

		double twoPointRatioZeroToThree = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointRatioZeroToThree(), thisWeekPlayerStat.getTwoPointRatioZeroToThree());
		double twoPointSuccessRateZeroToThree = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointSuccessRateZeroToThree(),
				thisWeekPlayerStat.getTwoPointSuccessRateZeroToThree());

		double twoPointRatioThreeToTen = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointRatioThreeToTen(), thisWeekPlayerStat.getTwoPointRatioThreeToTen());
		double twoPointSuccessRateThreeToTen = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointSuccessRateThreeToTen(),
				thisWeekPlayerStat.getTwoPointSuccessRateThreeToTen());

		double twoPointRatioTenToSixteen = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointRatioTenToSixteen(), thisWeekPlayerStat.getTwoPointRatioTenToSixteen());
		double twoPointSuccessRateTenToSixteen = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointSuccessRateTenToSixteen(),
				thisWeekPlayerStat.getTwoPointSuccessRateTenToSixteen());

		double twoPointRatioSixteenToThreePoint = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointRatioSixteenToThreePoint(),
				thisWeekPlayerStat.getTwoPointRatioSixteenToThreePoint());
		double twoPointSuccessRateSixteenToThreePoint = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTwoPointSuccessRateSixteenToThreePoint(),
				thisWeekPlayerStat.getTwoPointSuccessRateSixteenToThreePoint());

		double threePointRatio = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getThreePointRatio(), thisWeekPlayerStat.getThreePointRatio());
		double threePointSuccessRate = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getThreePointSuccessRate(), thisWeekPlayerStat.getThreePointSuccessRate());

		double onCourtMargin = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getOnCourtMargin(), thisWeekPlayerStat.getOnCourtMargin());
		double offCourtMargin = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getOffCourtMargin(), thisWeekPlayerStat.getOffCourtMargin());
		double netMargin = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getNetMargin(),
				thisWeekPlayerStat.getNetMargin());

		double per = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getPer(),
				thisWeekPlayerStat.getPer());
		double trueShootingPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTrueShootingPercentage(), thisWeekPlayerStat.getTrueShootingPercentage());
		double effectiveFieldGoalPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getEffectiveFieldGoalPercentage(),
				thisWeekPlayerStat.getEffectiveFieldGoalPercentage());

		double offensiveReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getOffensiveReboundPercentage(), thisWeekPlayerStat.getOffensiveReboundPercentage());
		double defensiveReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getDefensiveReboundPercentage(), thisWeekPlayerStat.getDefensiveReboundPercentage());
		double totalReboundPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTotalReboundPercentage(), thisWeekPlayerStat.getTotalReboundPercentage());
		double assistPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getAssistPercentage(), thisWeekPlayerStat.getAssistPercentage());
		double stealPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getStealPercentage(), thisWeekPlayerStat.getStealPercentage());
		double blockPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getBlockPercentage(), thisWeekPlayerStat.getBlockPercentage());

		double turnoverPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTurnoverPercentage(), thisWeekPlayerStat.getTurnoverPercentage());
		double assistTurnoverRatio = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getAssistTurnoverRatio(), thisWeekPlayerStat.getAssistTurnoverRatio());
		double usagePercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getUsagePercentage(), thisWeekPlayerStat.getUsagePercentage());

		double drivesStopped = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getDrivesStopped(), thisWeekPlayerStat.getDrivesStopped());
		double drivesFaced = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getDrivesFaced(), thisWeekPlayerStat.getDrivesFaced());
		double drivesStoppedPercentage = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getDrivesStoppedPercentage(), thisWeekPlayerStat.getDrivesStoppedPercentage());
		double turnoversForced = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTurnoversForced(), thisWeekPlayerStat.getTurnoversForced());
		double pointsAllowed = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getPointsAllowed(), thisWeekPlayerStat.getPointsAllowed());
		double shotsFaced = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getShotsFaced(),
				thisWeekPlayerStat.getShotsFaced());
		double pointsAllowedPerShotsFaced = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getPointsAllowedPerShotsFaced(), thisWeekPlayerStat.getPointsAllowedPerShotsFaced());
		double touches = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay, lastWeekPlayerStat.getTouches(),
				thisWeekPlayerStat.getTouches());
		double turnoversPerTouches = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTurnoversPerTouches(), thisWeekPlayerStat.getTurnoversPerTouches());
		double assistsPerTouches = weeklyAdjustInThree(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getAssistsPerTouches(), thisWeekPlayerStat.getAssistsPerTouches());
		double chargesTaken = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getChargesTaken(), thisWeekPlayerStat.getChargesTaken());
		double technicalFouls = weeklyAdjustInOne(lastWeekGamePlay, thisWeekGamePlay,
				lastWeekPlayerStat.getTechnicalFouls(), thisWeekPlayerStat.getTechnicalFouls());

		PlayerStatWeekly playerStatWeekly = PlayerStatWeekly.builder().assist(assist).assistPercentage(assistPercentage)
				.assistsPerTouches(assistsPerTouches).assistTurnoverRatio(assistTurnoverRatio).block(block)
				.blockPercentage(blockPercentage).chargesTaken(chargesTaken).defensiveRebound(defensiveRebound)
				.defensiveReboundPercentage(defensiveReboundPercentage).drivesFaced(drivesFaced)
				.drivesStopped(drivesStopped).drivesStoppedPercentage(drivesStoppedPercentage)
				.effectiveFieldGoalPercentage(effectiveFieldGoalPercentage).fieldGoalAttempt(fieldGoalAttempt)
				.fieldGoalMade(fieldGoalMade).fieldGoalPercentage(fieldGoalPercentage).foul(foul)
				.freeThrowAttempt(freeThrowAttempt).freeThrowMade(freeThrowMade)
				.freeThrowPercentage(freeThrowPercentage).gamePlay(gamePlay).gameStart(gameStart).min(min)
				.netMargin(netMargin).offCourtMargin(offCourtMargin).offensiveRebound(offensiveRebound)
				.offensiveReboundPercentage(offensiveReboundPercentage).onCourtMargin(onCourtMargin).per(per)
				.playerId(playerId).playerName(playerName).point(point).pointsAllowed(pointsAllowed)
				.pointsAllowedPerShotsFaced(pointsAllowedPerShotsFaced).position(position).shotsFaced(shotsFaced)
				.steal(steal).stealPercentage(stealPercentage).technicalFouls(technicalFouls)
				.threePointAttempt(threePointAttempt).threePointMade(threePointMade)
				.threePointPercentage(threePointPercentage).threePointRatio(threePointRatio)
				.threePointSuccessRate(threePointSuccessRate).totalRebound(totalRebound)
				.totalReboundPercentage(totalReboundPercentage).touches(touches)
				.trueShootingPercentage(trueShootingPercentage).turnover(turnover)
				.turnoverPercentage(turnoverPercentage).turnoversForced(turnoversForced)
				.turnoversPerTouches(turnoversPerTouches).twoPointRatio(twoPointRatio)
				.twoPointRatioSixteenToThreePoint(twoPointRatioSixteenToThreePoint)
				.twoPointRatioTenToSixteen(twoPointRatioTenToSixteen).twoPointRatioThreeToTen(twoPointRatioThreeToTen)
				.twoPointRatioZeroToThree(twoPointRatioZeroToThree).twoPointSuccessRate(twoPointSuccessRate)
				.twoPointSuccessRateSixteenToThreePoint(twoPointSuccessRateSixteenToThreePoint)
				.twoPointSuccessRateTenToSixteen(twoPointSuccessRateTenToSixteen)
				.twoPointSuccessRateThreeToTen(twoPointSuccessRateThreeToTen)
				.twoPointSuccessRateZeroToThree(twoPointSuccessRateZeroToThree).usagePercentage(usagePercentage)
				.version(version).build();
		return playerStatWeekly;
	}

	private double weeklyAdjustInOne(int lastWeekGamePlay, int thisWeekGamePlay, double lastWeekResult,
			double thisWeekResult) {
		double result = (thisWeekResult * thisWeekGamePlay - lastWeekResult * lastWeekGamePlay)
				/ (thisWeekGamePlay - lastWeekGamePlay);

		// 자리수 조절
		result = Math.round(result * 10) / 10.0;

		return result;
	}

	private double weeklyAdjustInThree(int lastWeekGamePlay, int thisWeekGamePlay, double lastWeekResult,
			double thisWeekResult) {
		double result = (thisWeekResult * thisWeekGamePlay - lastWeekResult * lastWeekGamePlay)
				/ (thisWeekGamePlay - lastWeekGamePlay);

		// 자리수 조절
		result = Math.round(result * 1000) / 1000.0;

		return result;
	}

	// 비율 스탯 말고 갯수 스탯 경기수로 총합산 조져서 확실하게 구하기 (3점슛 등)
	private double calculateVolumeByGameCount(int lastWeekGamePlay, int thisWeekGamePlay, double lastWeekStat,
			double thisWeekStat) {
		double result = (thisWeekGamePlay * lastWeekStat - lastWeekGamePlay * lastWeekStat)
				/ (thisWeekGamePlay - lastWeekGamePlay);
		// 자리수 조절
		result = numberConvertToOneCipher(result);
		return result;
	}

	private double numberConvertToOneCipher(double number) {
		return Math.round(number * 10) / 10.0;
	}

	private PlayerStatWeekly adjustShootRatio(PlayerStat lastWeekPlayerStat, PlayerStat thisWeekPlayerStat,
			PlayerStatWeekly playerStatWeekly) {

		double lastWeekFeildGoalAttempt = lastWeekPlayerStat.getFieldGoalAttempt();
		double lastWeekFeildGoalMade = lastWeekPlayerStat.getFieldGoalMade();
		double thisWeekFeildGoalAttempt = thisWeekPlayerStat.getFieldGoalAttempt();
		double thisWeekFeildGoalMade = thisWeekPlayerStat.getFieldGoalAttempt();

		double twoPointRatioLastWeek = lastWeekPlayerStat.getTwoPointRatio();
		double twoPointSuccessRateLastWeek = lastWeekPlayerStat.getTwoPointSuccessRate();

		double twoPointRatioThisWeek = thisWeekPlayerStat.getTwoPointRatio();
		double twoPointSuccessRateThisWeek = thisWeekPlayerStat.getTwoPointSuccessRate();

		double twoPointRatioZeroToThreeLastWeek = lastWeekPlayerStat.getTwoPointRatioZeroToThree();
		double twoPointSuccessRateZeroToThreeLastWeek = lastWeekPlayerStat.getTwoPointSuccessRateZeroToThree();

		double twoPointRatioZeroToThreeThisWeek = thisWeekPlayerStat.getTwoPointRatioZeroToThree();
		double twoPointSuccessRateZeroToThreeThisWeek = thisWeekPlayerStat.getTwoPointSuccessRateZeroToThree();

		double twoPointRatioThreeToTenLastWeek = lastWeekPlayerStat.getTwoPointRatioThreeToTen();
		double twoPointSuccessRateThreeToTenLastWeek = lastWeekPlayerStat.getTwoPointSuccessRateThreeToTen();

		double twoPointRatioThreeToTenThisWeek = thisWeekPlayerStat.getTwoPointRatioThreeToTen();
		double twoPointSuccessRateThreeToTenThisWeek = thisWeekPlayerStat.getTwoPointSuccessRateThreeToTen();

		double twoPointRatioTenToSixteenLastWeek = lastWeekPlayerStat.getTwoPointRatioTenToSixteen();
		double twoPointSuccessRateTenToSixteenLastWeek = lastWeekPlayerStat.getTwoPointSuccessRateTenToSixteen();

		double twoPointRatioTenToSixteenThisWeek = thisWeekPlayerStat.getTwoPointRatioTenToSixteen();
		double twoPointSuccessRateTenToSixteenThisWeek = thisWeekPlayerStat.getTwoPointSuccessRateTenToSixteen();

		double twoPointRatioSixteenToThreePointLastWeek = lastWeekPlayerStat.getTwoPointRatioSixteenToThreePoint();
		double twoPointSuccessRateSixteenToThreePointLastWeek = lastWeekPlayerStat
				.getTwoPointSuccessRateSixteenToThreePoint();

		double twoPointRatioSixteenToThreePointThisWeek = thisWeekPlayerStat.getTwoPointRatioSixteenToThreePoint();
		double twoPointSuccessRateSixteenToThreePointThisWeek = thisWeekPlayerStat
				.getTwoPointSuccessRateSixteenToThreePoint();

		double threePointRatioLastWeek = lastWeekPlayerStat.getThreePointRatio();
		double threePointSuccessRateLastWeek = lastWeekPlayerStat.getThreePointSuccessRate();

		double threePointRatioThisWeek = thisWeekPlayerStat.getThreePointRatio();
		double threePointSuccessRateThisWeek = thisWeekPlayerStat.getThreePointSuccessRate();

		return playerStatWeekly;
	}

	private double[] shootRatio(int lastWeekFeildGoalAttempt, int lastWeekFeildGoalMade, int thisWeekFeildGoalAttempt,
			int thisWeekFeildGoalMade, double lastWeekRatio, double thisWeekRatio, double lastWeekSuccessRate,
			double thisWeekSuccessRate) {
		double[] result = null;

		double lastWeekAttempt = lastWeekFeildGoalAttempt * lastWeekRatio;
		double lastWeekMade = lastWeekAttempt * lastWeekSuccessRate;

		double thisWeekAttempt = thisWeekFeildGoalAttempt * thisWeekRatio - lastWeekAttempt;
		double thisWeekMade = thisWeekFeildGoalMade * thisWeekRatio * thisWeekSuccessRate - lastWeekMade;

		double thisWeekRatioArea = thisWeekAttempt / (thisWeekFeildGoalAttempt - lastWeekFeildGoalAttempt);

		thisWeekRatioArea = Math.round(thisWeekRatioArea * 1000) / 100.0;

		double thisWeekSuccessRateArea = thisWeekMade / thisWeekAttempt;

		thisWeekSuccessRateArea = Math.round(thisWeekSuccessRateArea * 1000) / 100.0;

		result[0] = thisWeekRatioArea;
		result[1] = thisWeekSuccessRateArea;

		return result;
	}
}
