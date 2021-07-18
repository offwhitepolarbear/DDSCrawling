package com.kihwangkwon.businesslogic.game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kihwangkwon.businesslogic.game.domain.PlayerGameStat.PlayerGameStatBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeamGameStat {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int version;
	
	int gameNumber;
	String teamName;
	String opponentTeamName;
	boolean home;
	boolean win;
	boolean overtime;
	int fieldGoalMade;
	int fieldGoalAttempt;
	int threePointMade;
	int threePointAttempt;
	int freeThrowMade;
	int freeThrowAttempt;
	int offensiveRebound;
	int defensiveRebound;
	int totalRebound;
	int assist;
	int steal;
	int block;
	int turnover;
	int foul;
	int point;
	
	@Builder
	public TeamGameStat(int gameNumber,
						String teamName,
						String opponentTeamName,
						boolean home,
						boolean win,
						boolean overtime,
						int fieldGoalMade,
						int fieldGoalAttempt,
						int threePointMade,
						int threePointAttempt,
						int freeThrowMade,
						int freeThrowAttempt,
						int offensiveRebound,
						int defensiveRebound,
						int totalRebound,
						int assist,
						int steal,
						int block,
						int turnover,
						int foul,
						int point) {
		
		this.gameNumber = gameNumber;
		this.teamName = teamName;
		this.opponentTeamName = opponentTeamName;
		this.home = home;
		this.win = win;
		this.overtime = overtime;
		this.fieldGoalMade = fieldGoalMade;
		this.fieldGoalAttempt = fieldGoalAttempt;
		this.threePointMade = threePointMade;
		this.threePointAttempt = threePointAttempt;
		this.freeThrowMade = freeThrowMade;
		this.freeThrowAttempt = freeThrowAttempt;
		this.offensiveRebound = offensiveRebound;
		this.defensiveRebound = defensiveRebound;
		this.totalRebound = totalRebound;
		this.assist = assist;
		this.steal = steal;
		this.block =block;
		this.turnover = turnover;
		this.foul = foul;
		this.point = point;
	}
}
