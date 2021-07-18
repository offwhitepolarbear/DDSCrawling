package com.kihwangkwon.businesslogic.team.domain;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Builder
@Getter
@Setter
public class TeamInfo {
	
	String name;
	String teamTag;
	String conference;
	String division;
	
	String gm;
	
	int win;
	int lose;
	double pointPerGame;
	double pointAllowedPerGame;
	double assistPerGame;
	double reboundPerGame;
	double blockPerGame;
	double stealPerGame;
	double turnoverPerGame;
	double fieldGoalPercent;
	double threePointPercent;
	double freeThrowPercent;
	
}
