package com.kihwangkwon.businesslogic.player.service;

public interface PlayerService {
	void getPlayerIndex();
	void getPlayerStatFromTeam();
	void getPlayerRatingListFromTeam();
	void savePlayerStatWeeklyAll(int lastWeekVersion, int thisWeekVersion, int week);
}
