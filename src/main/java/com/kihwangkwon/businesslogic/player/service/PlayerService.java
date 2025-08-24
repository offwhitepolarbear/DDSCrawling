package com.kihwangkwon.businesslogic.player.service;

public interface PlayerService {
	void getPlayerIndex(int version);
	void getPlayerStatFromTeam(int version);
	void getPlayerRatingListFromTeam();
	void savePlayerStatWeeklyAll(int lastWeekVersion, int thisWeekVersion, int week);
	void savePlayerStatFromLocalFiles();
}
