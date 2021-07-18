package com.kihwangkwon.businesslogic.player.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class PlayerStat {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	int version;
	
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
	public PlayerStat(int version
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
		
	}
	
}
