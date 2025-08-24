package com.kihwangkwon.businesslogic.game.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
	public void getGame(String gameId, int version) {
		List<TeamStat> homeAndAway= htmlParserGame.getGame(gameId);
		
		//홈팀 저장
		saveTeamStat(homeAndAway.get(0), version);
		//원정팀 저장
		saveTeamStat(homeAndAway.get(1), version);
	
	}
	
	private void saveTeamStat(TeamStat teamStat, int version) {
		//팀 스탯 저장
		setVersionToTeamStat(teamStat, version);
		teamGameStatRepository.save(teamStat.getTeamGameStat());
		
		//돌면서 플레이어 스탯 저장
		for(PlayerGameStat playerGameStat : teamStat.getPlayerGameStatList()) {
			playerGameStatRepository.save(playerGameStat);
		}
		
	}
	
	private void setVersionToTeamStat(TeamStat teamStat, int version) {
		TeamGameStat teamGameStat = teamStat.getTeamGameStat();
		teamGameStat.setVersion(version);
		List<PlayerGameStat> playerGameStatList = teamStat.getPlayerGameStatList();
		playerGameStatList.forEach(playerGameStat -> playerGameStat.setVersion(version));
	}
	

	@Override
	public void getGameIds(int version) {
		TeamURLTag[] all = TeamURLTag.values();

		for(TeamURLTag teamTag:all) {
			String teamName = teamTag.toString();
			if(teamName.equals("PORTrail_Blazers")) {
				teamName = "PORTrailBlazers";
			}
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
	
	private List<GameId> selectAllGameId(int version) {
		return gameIdRepository.findByVersionOrderByGameIdAsc(version);
	}

	@Override
	public void getAllGameData(int version) {
		List<GameId> gameIdList = selectAllGameId(version);
		gameIdList.stream().parallel().forEach((GameId gameId)-> getGame(String.valueOf(gameId.getGameId()), version));
	}
}
