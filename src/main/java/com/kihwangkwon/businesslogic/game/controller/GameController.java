package com.kihwangkwon.businesslogic.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kihwangkwon.businesslogic.game.service.GameService;

@RequestMapping("/rest/game")
@RestController
public class GameController {

	
	private GameService gameService;
	
	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	
	public void playerGamestat() {
		
	}
	
	public void gameStat() {
		
	}
	
	@RequestMapping("/{gameId}")
	public void gameCrawling(@PathVariable("gameId") String gameId) {
		gameService.getGame(gameId);
	}
	
	@RequestMapping("/gameIds")
	public void gameCrawling() {
		//정상 작동하는데 또 긁어올까봐 막아둠
		//gameService.getGameIds();
	}
	
	
	
	@RequestMapping("/all")
	public void allGameCrawling() {
		
		//정상 작동하는데 또 긁어올까봐 막아둠
		//gameService.getAllGameData();
	}
	
}