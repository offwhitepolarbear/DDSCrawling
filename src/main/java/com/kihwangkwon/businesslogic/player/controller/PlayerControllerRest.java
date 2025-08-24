package com.kihwangkwon.businesslogic.player.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kihwangkwon.businesslogic.localfile.LocalFileReaderInterface;
import com.kihwangkwon.businesslogic.player.service.PlayerService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/rest/player")
@RestController
@RequiredArgsConstructor
public class PlayerControllerRest {
	
	private final PlayerService playerService;
	private final LocalFileReaderInterface localFileReaderInterface;
	private final int version = 20250604;
	private int lastVersion = 20221004;
	private int newVersion = 20221005;
	private int thisWeek = 4;
		
	@RequestMapping("/index")
	public void getPlayerId() {
		//정상작동 작동방지
		playerService.getPlayerIndex(version);
	}
	
	@RequestMapping("/stat/team")
	public void getPlayerStatFromTeam() {
		//정상작동 작동방지
		playerService.getPlayerStatFromTeam(version);
	}
	
	@RequestMapping("/rating/team")
	public void getPlayerRating() {
		//정상작동 작동방지
		playerService.getPlayerRatingListFromTeam();
	}
	
	@RequestMapping("/weekly/stat")
	public void getWeeklyStat() {
		//정상작동 작동방지
//		playerService.savePlayerStatWeeklyAll(lastVersion,newVersion,thisWeek);
	}
	
	@RequestMapping("/fff")
	public void getFileRead() throws IOException {
		playerService.savePlayerStatFromLocalFiles();
	}
}
