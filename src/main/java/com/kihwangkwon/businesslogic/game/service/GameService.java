package com.kihwangkwon.businesslogic.game.service;

public interface GameService {
	void getGame (String gameId);
	void getGameIds(int version);
	void getAllGameData(int version);
}
