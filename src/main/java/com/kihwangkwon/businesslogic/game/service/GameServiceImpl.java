package com.kihwangkwon.businesslogic.game.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.game.GameApi;
import com.kihwangkwon.businesslogic.game.domain.GameId;
import com.kihwangkwon.businesslogic.game.domain.PlayerGameStat;
import com.kihwangkwon.businesslogic.game.domain.TeamGameStat;
import com.kihwangkwon.businesslogic.game.domain.TeamStat;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;
@Service
public class GameServiceImpl implements GameService {

	private GameApi gameApi;
	
	private PlayerGameStatRepository playerGameStatRepository;
	private TeamGameStatRepository teamGameStatRepository;
	private GameIdRepository gameIdRepository;
	private HtmlParserGame htmlParserGame;
	
	@Autowired
	public GameServiceImpl(GameApi gameApi
			, PlayerGameStatRepository playerGameStatRepository
			, TeamGameStatRepository teamGameStatRepository
			, GameIdRepository gameIdRepository
			, HtmlParserGame htmlParserGame) {
		this.gameApi = gameApi;
		this.playerGameStatRepository = playerGameStatRepository;
		this.teamGameStatRepository = teamGameStatRepository;
		this.gameIdRepository = gameIdRepository;
		this.htmlParserGame = htmlParserGame;
	}
	
	@Override
	public void getGame(String gameId) {
		List<TeamStat> homeAndAway= htmlParserGame.getGame(gameId);
		
		//홈팀 저장
		saveTeamStat(homeAndAway.get(0));
		//원정팀 저장
		saveTeamStat(homeAndAway.get(1));
	
	}
	
	private void saveTeamStat(TeamStat teamStat) {
		//팀 스탯 저장
		teamGameStatRepository.save(teamStat.getTeamGameStat());
		
		//돌면서 플레이어 스탯 저장
		for(PlayerGameStat playerGameStat : teamStat.getPlayerGameStatList()) {
			playerGameStatRepository.save(playerGameStat);
		}
		
	}
	

	@Override
	public void getGameIds(int version) {
		TeamURLTag[] all = TeamURLTag.values();

		for(TeamURLTag teamTag:all) {
			String teamName = teamTag.toString();
			Document document = gameApi.getGameList(teamName);
			List idList = getGameIdList(document);
			saveGameIds(idList, version);
		}
	}

	private List<Integer> getGameIdList(Document document){
		List<Integer> idList = new ArrayList<Integer>();
		Iterator<Element> gameIds= document.select("td[data-label=Result] > a").iterator();
		while(gameIds.hasNext()) {
			String gameId = gameIds.next().attr("href");
			gameId = gameId.replace("Game","");
			gameId = gameId.replace(".html","");
			idList.add(Integer.parseInt(gameId));
		}
		return idList;
	}
	
	private void saveGameIds(List<Integer> idList, int version){
		
		for(int id: idList) {
			GameId dbGameId = gameIdRepository.findByVersionAndGameId(version, id);
			if(dbGameId==null) {
				GameId gameId = new GameId();
				gameId.setGameId(id);
				gameId.setVersion(version);
				gameIdRepository.save(gameId);
			}
		}
		
	}
	
	private List selectAllGameId(int version) {
		return gameIdRepository.findByVersionOrderByGameIdAsc(version);
	}

	@Override
	public void getAllGameData(int version) {
		List<GameId> gameIdList = selectAllGameId(version);
		for(GameId gameId : gameIdList) {
			String gameIdString = String.valueOf(gameId.getGameId());
			getGame(gameIdString);
		}
	}
}
