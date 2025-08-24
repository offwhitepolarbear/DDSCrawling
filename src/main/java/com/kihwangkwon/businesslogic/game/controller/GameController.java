package com.kihwangkwon.businesslogic.game.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kihwangkwon.businesslogic.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/rest/game")
@RestController
@RequiredArgsConstructor

public class GameController {
	
	private final int version = 20250604;
	
	private final GameService gameService;
		
//	@RequestMapping("/{gameId}")
//	public void gameCrawling(@PathVariable("gameId") String gameId) {
//		gameService.getGame(gameId);
//	}
	
	@RequestMapping("/gameIds")
	public void gameCrawling() {
		//정상 작동하는데 또 긁어올까봐 막아둠
		gameService.getGameIds(version);
	}
	
	@RequestMapping("/all")
	public void allGameCrawling() {
		//정상 작동하는데 또 긁어올까봐 막아둠
		gameService.getAllGameData(version);
	}
	
}