package com.kihwangkwon.businesslogic.game.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TeamStat {
	TeamGameStat teamGameStat;
	List<PlayerGameStat> playerGameStatList;
}
