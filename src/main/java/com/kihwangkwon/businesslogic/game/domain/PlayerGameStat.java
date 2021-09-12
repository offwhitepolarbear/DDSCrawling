package com.kihwangkwon.businesslogic.game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

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
public class PlayerGameStat {
	
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
	boolean starter;
	String playerId;
	String playerName;
	String position;
	int min;
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
	int margin;
	
	@Builder
	public PlayerGameStat(int version
							, int gameNumber
							, String teamName
							, String opponentTeamName
							, boolean home
							, boolean win
							, boolean overtime
							, boolean starter
							, String playerId
							, String playerName
							, String position
							, int min
							, int fieldGoalMade
							, int fieldGoalAttempt
							, int threePointMade
							, int threePointAttempt
							, int freeThrowMade
							, int freeThrowAttempt
							, int offensiveRebound
							, int defensiveRebound
							, int totalRebound
							, int assist
							, int steal
							, int block
							, int turnover
							, int foul
							, int point
							, int margin){
		this.version = version;
		this.gameNumber = gameNumber;
		this.teamName = teamName;
		this.opponentTeamName = opponentTeamName;
		this.home = home;
		this.win = win;
		this.overtime = overtime;
		this.starter = starter;
		this.playerId = playerId;
		this.playerName = playerName;
		this.position = position;
		this.min = min;
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
		this.block = block;
		this.turnover = turnover;
		this.foul = foul;
		this.point = point;
		this.margin = margin;
	}
	
}
