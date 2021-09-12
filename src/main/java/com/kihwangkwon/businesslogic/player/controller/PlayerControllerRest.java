package com.kihwangkwon.businesslogic.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kihwangkwon.businesslogic.player.service.PlayerService;

@RequestMapping("/rest/player")
@RestController
public class PlayerControllerRest {
	
	private PlayerService playerService;

	private int lastVersion = 20210901;
	private int newVersion = 20210902;
	private int thisWeek = 2;
	
	
	@Autowired
	public PlayerControllerRest(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	
	@RequestMapping("/index")
	public void getPlayerId() {
		//정상작동 작동방지
		playerService.getPlayerIndex();
	}
	
	@RequestMapping("/stat/team")
	public void getPlayerStatFromTeam() {
		//정상작동 작동방지
		playerService.getPlayerStatFromTeam();
	}
	
	@RequestMapping("/rating/team")
	public void getPlayerRating() {
		//정상작동 작동방지
		playerService.getPlayerRatingListFromTeam();
	}
	
	@RequestMapping("/weekly/stat")
	public void getWeeklyStat() {
		//정상작동 작동방지
		playerService.savePlayerStatWeeklyAll(lastVersion,newVersion,thisWeek);
	}
}
