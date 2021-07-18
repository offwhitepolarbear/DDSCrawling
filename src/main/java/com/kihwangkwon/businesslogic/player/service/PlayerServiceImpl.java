package com.kihwangkwon.businesslogic.player.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.player.PlayerApi;
import com.kihwangkwon.businesslogic.player.domain.PlayerId;
import com.kihwangkwon.businesslogic.player.domain.PlayerRating;
import com.kihwangkwon.businesslogic.player.domain.PlayerStat;
import com.kihwangkwon.businesslogic.player.repository.PlayerIdRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerRatingRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerStatRepository;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;

@Service
public class PlayerServiceImpl implements PlayerService {

	private PlayerApi playerApi;

	private PlayerHtmlParser playerHtmlParser;
	
	private PlayerIdRepository playerRepository;
	private PlayerStatRepository playerStatRepository;
	private PlayerRatingRepository playerRatingRepository;
	
	
	@Autowired
	public PlayerServiceImpl(PlayerApi playerApi
			, PlayerIdRepository playerRepository
			, PlayerStatRepository playerStatRepository
			, PlayerRatingRepository playerRatingRepository
			, PlayerHtmlParser playerHtmlParser) {
		this.playerApi = playerApi;
		this.playerRepository = playerRepository;
		this.playerStatRepository = playerStatRepository;
		this.playerRatingRepository = playerRatingRepository;
		this.playerHtmlParser = playerHtmlParser;
	}
	
	@Override
	public void getPlayerIndex() {
		Document document = playerApi.getPlayerIndex();
		
		
		Iterator<Element> nameIterator = document.select("td > a").not("a[style=text-decoration: none]").iterator();
		
		while(nameIterator.hasNext()) {
			Element playerName = nameIterator.next();
			boolean emptyCheck = playerName.text().equals("");
			boolean weiredHtmlCheck = playerName.text().startsWith("http:");
			if(!emptyCheck && !weiredHtmlCheck) {
				PlayerId playerId= elementToPlayerId(playerName);
				playerRepository.save(playerId);
			}
		}
	}

	private PlayerId elementToPlayerId(Element element) {
		
		//이름 파싱
		//,로 스플릿
		String[] splitName = element.text().split("\\,");
		// 성 이름 순서 바꿔서 등록
		String playerName = splitName[1].replaceAll(" ","")+" "+splitName[0].replaceAll(" ","");
		
		
		//선수id 파싱
		String htmlId = element.attr("href");
		//id 뒤에 .html 삭제
		htmlId = htmlId.substring(0,htmlId.length()-5);
		
		PlayerId playerId = PlayerId.builder().playerName(playerName).playerId(htmlId).build();
		return playerId;
	}
	
	
	
	@Override
	public void getPlayerStatFromTeam() {
		
		
		//전체 팀 태그 가져오기
		TeamURLTag[] teamUrls = TeamURLTag.values();
		
		// 팀별로 반복
		for(TeamURLTag teamTag:teamUrls) {;
			List<PlayerStat> playerStatList = playerHtmlParser.getPlayerStatFromTeamPage(teamTag);
			
			//팀별 가져온 스탯 저장
			savePlayerStatFromTeamPage(playerStatList);
			
		}
	}
	
	private void savePlayerStatFromTeamPage(List<PlayerStat> playerStatList) {
		for(PlayerStat playerStat : playerStatList) {
			playerStatRepository.save(playerStat);
		}
	}
	

	@Override
	public void getPlayerRatingListFromTeam() {
		for(TeamURLTag teamTag: TeamURLTag.values()) {
			List<PlayerRating> playerRatingList = playerHtmlParser.getPlayerRatingList(teamTag);
			savePlayerRatingListTeam(playerRatingList);
		}
	}
	
	private void savePlayerRatingListTeam(List<PlayerRating> playerRatingList) {
		for (PlayerRating playerRating : playerRatingList) {
			playerRatingRepository.save(playerRating);
		}
	}
	
}
