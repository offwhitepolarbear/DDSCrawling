package com.kihwangkwon.businesslogic.player.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kihwangkwon.businesslogic.player.domain.PlayerStat.PlayerStatBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
public class PlayerStatWeekly {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	int version;
	int week;
	
	String playerId;
	String playerName;
	String position;

	int gamePlay;
	int gameStart;
	double min;
	
	double fieldGoalMade;
	double fieldGoalAttempt;
	double fieldGoalPercentage;
	
	double threePointMade;
	double threePointAttempt;
	double threePointPercentage;
	
	double freeThrowMade;
	double freeThrowAttempt;
	double freeThrowPercentage;
	
	double offensiveRebound;
	double defensiveRebound;
	double totalRebound;
	double assist;
	double steal;
	double block;
	double turnover;
	double foul;
	double point;
	
	double twoPointRatio;
	double twoPointSuccessRate;
	
	double twoPointRatioZeroToThree;
	double twoPointSuccessRateZeroToThree;
	
	double twoPointRatioThreeToTen;
	double twoPointSuccessRateThreeToTen;
	
	double twoPointRatioTenToSixteen;
	double twoPointSuccessRateTenToSixteen;
	
	double twoPointRatioSixteenToThreePoint;
	double twoPointSuccessRateSixteenToThreePoint;
	
	double threePointRatio;
	double threePointSuccessRate;
	
	double onCourtMargin;
	double offCourtMargin;
	double netMargin;

	double per;
	double trueShootingPercentage;
	double effectiveFieldGoalPercentage; 
	
	double offensiveReboundPercentage;
	double defensiveReboundPercentage;
	double totalReboundPercentage;
	double assistPercentage;
	double stealPercentage;
	double blockPercentage;

	double turnoverPercentage;
	double assistTurnoverRatio;
	double usagePercentage;
	
	double drivesStopped;
	double drivesFaced;
	double drivesStoppedPercentage;
	double turnoversForced;
	double pointsAllowed;
	double shotsFaced;
	double pointsAllowedPerShotsFaced;
	double touches;
	double turnoversPerTouches;
	double assistsPerTouches;
	double chargesTaken;
	double technicalFouls;
	
	@Builder
	public PlayerStatWeekly(int version
						, String playerId
						, String playerName
						, String position
						, int gamePlay
						, int gameStart
						, double min
						, double fieldGoalMade
						, double fieldGoalAttempt
						, double fieldGoalPercentage
						, double threePointMade
						, double threePointAttempt
						, double threePointPercentage
						, double freeThrowMade
						, double freeThrowAttempt
						, double freeThrowPercentage
						, double offensiveRebound
						, double defensiveRebound
						, double totalRebound
						, double assist
						, double steal
						, double block
						, double turnover
						, double foul
						, double point
						, double twoPointRatio
						, double twoPointSuccessRate
						, double twoPointRatioZeroToThree
						, double twoPointSuccessRateZeroToThree
						, double twoPointRatioThreeToTen
						, double twoPointSuccessRateThreeToTen
						, double twoPointRatioTenToSixteen
						, double twoPointSuccessRateTenToSixteen
						, double twoPointRatioSixteenToThreePoint
						, double twoPointSuccessRateSixteenToThreePoint
						, double threePointRatio
						, double threePointSuccessRate
						, double onCourtMargin
						, double offCourtMargin
						, double netMargin
						, double per
						, double trueShootingPercentage
						, double effectiveFieldGoalPercentage
						, double offensiveReboundPercentage
						, double defensiveReboundPercentage
						, double totalReboundPercentage
						, double assistPercentage
						, double stealPercentage
						, double blockPercentage
						, double turnoverPercentage
						, double assistTurnoverRatio
						, double usagePercentage
						, double drivesStopped
						, double drivesFaced
						, double drivesStoppedPercentage
						, double turnoversForced
						, double pointsAllowed
						, double shotsFaced
						, double pointsAllowedPerShotsFaced
						, double touches
						, double turnoversPerTouches
						, double assistsPerTouches
						, double chargesTaken
						, double technicalFouls) {
		this.version = version;
		this.playerId =  playerId ;
		this.playerName =  playerName ;
		this.position = position ;
		this.gamePlay =  gamePlay ;
		this.gameStart =  gameStart ;
		this.min =  min ;
		this.fieldGoalMade =  fieldGoalMade ;
		this.fieldGoalAttempt =  fieldGoalAttempt ;
		this.fieldGoalPercentage =  fieldGoalPercentage ;
		this.threePointMade =  threePointMade ;
		this.threePointAttempt =  threePointAttempt ;
		this.threePointPercentage =  threePointPercentage ;
		this.freeThrowMade =  freeThrowMade ;
		this.freeThrowAttempt =  freeThrowAttempt ;
		this.freeThrowPercentage =  freeThrowPercentage ;
		this.offensiveRebound =  offensiveRebound ;
		this.defensiveRebound =  defensiveRebound ;
		this.totalRebound =  totalRebound ;
		this.assist =  assist ;
		this.steal =  steal ;
		this.block =  block ;
		this.turnover =  turnover ;
		this.foul =  foul ;
		this.point =  point ;
		this.twoPointRatio =  twoPointRatio ;
		this.twoPointSuccessRate =  twoPointSuccessRate ;
		this.twoPointRatioZeroToThree =  twoPointRatioZeroToThree ;
		this.twoPointSuccessRateZeroToThree =  twoPointSuccessRateZeroToThree ;
		this.twoPointRatioThreeToTen =  twoPointRatioThreeToTen ;
		this.twoPointSuccessRateThreeToTen =  twoPointSuccessRateThreeToTen ;
		this.twoPointRatioTenToSixteen =  twoPointRatioTenToSixteen ;
		this.twoPointSuccessRateTenToSixteen =  twoPointSuccessRateTenToSixteen ;
		this.twoPointRatioSixteenToThreePoint =  twoPointRatioSixteenToThreePoint ;
		this.twoPointSuccessRateSixteenToThreePoint =  twoPointSuccessRateSixteenToThreePoint ;
		this.threePointRatio =  threePointRatio ;
		this.threePointSuccessRate =  threePointSuccessRate ;
		this.onCourtMargin =  onCourtMargin ;
		this.offCourtMargin =  offCourtMargin ;
		this.netMargin =  netMargin ;
		this.per =  per ;
		this.trueShootingPercentage =  trueShootingPercentage ;
		this.effectiveFieldGoalPercentage =  effectiveFieldGoalPercentage ;
		this.offensiveReboundPercentage =  offensiveReboundPercentage ;
		this.defensiveReboundPercentage =  defensiveReboundPercentage ;
		this.totalReboundPercentage =  totalReboundPercentage ;
		this.assistPercentage =  assistPercentage ;
		this.stealPercentage =  stealPercentage ;
		this.blockPercentage =  blockPercentage ;
		this.turnoverPercentage =  turnoverPercentage ;
		this.assistTurnoverRatio =  assistTurnoverRatio ;
		this.usagePercentage =  usagePercentage ;
		this.drivesStopped =  drivesStopped ;
		this.drivesFaced =  drivesFaced ;
		this.drivesStoppedPercentage =  drivesStoppedPercentage ;
		this.turnoversForced =  turnoversForced ;
		this.pointsAllowed =  pointsAllowed ;
		this.shotsFaced =  shotsFaced ;
		this.pointsAllowedPerShotsFaced =  pointsAllowedPerShotsFaced ;
		this.touches =  touches ;
		this.turnoversPerTouches =  turnoversPerTouches ;
		this.assistsPerTouches =  assistsPerTouches ;
		this.chargesTaken =  chargesTaken ;
		this.technicalFouls =  technicalFouls ;
		}
}
