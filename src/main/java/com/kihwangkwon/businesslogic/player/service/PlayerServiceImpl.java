package com.kihwangkwon.businesslogic.player.service;

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.player.PlayerApi;
import com.kihwangkwon.businesslogic.localfile.LocalFileInfo;
import com.kihwangkwon.businesslogic.localfile.LocalFileReaderInterface;
import com.kihwangkwon.businesslogic.player.domain.PlayerId;
import com.kihwangkwon.businesslogic.player.domain.PlayerRating;
import com.kihwangkwon.businesslogic.player.domain.PlayerStat;
import com.kihwangkwon.businesslogic.player.domain.PlayerStatWeekly;
import com.kihwangkwon.businesslogic.player.repository.PlayerIdRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerRatingRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerStatRepository;
import com.kihwangkwon.businesslogic.player.repository.PlayerStatWeeklyRepository;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

	private final PlayerApi playerApi;
	private final PlayerHtmlParser playerHtmlParser;
	private final PlayerStatWeeklyParser playerStatWeeklyParser;
	private final PlayerIdRepository playerRepository;
	private final PlayerStatRepository playerStatRepository;
	private final PlayerRatingRepository playerRatingRepository;
	private final PlayerStatWeeklyRepository playerStatWeeklyRepository;
	private final LocalFileReaderInterface localFileReaderInterface;

//	@Autowired
//	public PlayerServiceImpl(PlayerApi playerApi
//			, PlayerIdRepository playerRepository
//			, PlayerStatRepository playerStatRepository
//			, PlayerRatingRepository playerRatingRepository
//			, PlayerStatWeeklyRepository playerStatWeeklyRepository
//			, PlayerHtmlParser playerHtmlParser
//			, PlayerStatWeeklyParser playerStatWeeklyParser) {
//		this.playerApi = playerApi;
//		this.playerRepository = playerRepository;
//		this.playerStatRepository = playerStatRepository;
//		this.playerRatingRepository = playerRatingRepository;
//		this.playerStatWeeklyRepository = playerStatWeeklyRepository;
//		this.playerHtmlParser = playerHtmlParser;
//		this.playerStatWeeklyParser = playerStatWeeklyParser;
//	}

	@Override
	public void getPlayerIndex(int version) {
		Document document = playerApi.getPlayerIndex();

		Iterator<Element> nameIterator = document.select("td > a").not("a[style=text-decoration: none]").iterator();

		while (nameIterator.hasNext()) {
			Element playerName = nameIterator.next();
			boolean emptyCheck = playerName.text().equals("");
			boolean weiredHtmlCheck = playerName.text().startsWith("http:");
			if (!emptyCheck && !weiredHtmlCheck) {
				PlayerId playerId = elementToPlayerId(playerName);
				playerId.setVersion(version);
				playerRepository.save(playerId);
			}
		}
	}

	private PlayerId elementToPlayerId(Element element) {
		// 이름 파싱
		// ,로 스플릿
		String[] splitName = element.text().split("\\,");
		// 성 이름 순서 바꿔서 등록
		String playerName = splitName[1].replaceAll(" ", "") + " " + splitName[0].replaceAll(" ", "");

		// 선수id 파싱
		String htmlId = element.attr("href");
		// id 뒤에 .html 삭제
		// htmlId = htmlId.substring(0,htmlId.length()-5);
		htmlId = htmlId.replace(".html", "");

		PlayerId playerId = PlayerId.builder().playerName(playerName).playerId(htmlId).build();
		return playerId;
	}

	@Override
	public void getPlayerStatFromTeam(int version) {

		// 전체 팀 태그 가져오기
		TeamURLTag[] teamUrls = TeamURLTag.values();

		// 팀별로 반복
		for (TeamURLTag teamTag : teamUrls) {
			String teamString = teamTag.toString();
			
			if(teamTag.equals(TeamURLTag.PORTrailblazers)) {
//				teamString = "PORTrailblazers";
			}
			List<PlayerStat> playerStatList = playerHtmlParser.getPlayerStatFromTeamPage(teamString);
			String teamNameAcronyms = teamTag.toString().substring(0,3);
			// 팀별 가져온 스탯 저장
			savePlayerStatFromTeamPage(playerStatList, teamNameAcronyms, version);

		}
	}

	private void savePlayerStatFromTeamPage(List<PlayerStat> playerStatList, String teamNameAcronyms, int version) {
		playerStatList.stream().parallel().forEach(playerStat -> savePlayerStatWithTeamName(playerStat, teamNameAcronyms, version));
	}
	
	private void savePlayerStatWithTeamName(PlayerStat playerStat, String teamNameAcronyms, int version) {
		playerStat.setTeamName(teamNameAcronyms);
		playerStat.setVersion(version);
		playerStatRepository.save(playerStat);
	}

	@Override
	public void getPlayerRatingListFromTeam() {
		for (TeamURLTag teamTag : TeamURLTag.values()) {
			List<PlayerRating> playerRatingList = playerHtmlParser.getPlayerRatingList(teamTag);
			savePlayerRatingListTeam(playerRatingList);
		}
	}

	private void savePlayerRatingListTeam(List<PlayerRating> playerRatingList) {
		for (PlayerRating playerRating : playerRatingList) {
			playerRatingRepository.save(playerRating);
		}
	}

	public void savePlayerStatWeeklyAll(int lastWeekVersion, int thisWeekVersion, int week) {

		List lastWeekStats = playerStatRepository.findByVersion(lastWeekVersion);
		List thisWeekStats = playerStatRepository.findByVersion(thisWeekVersion);
		findSamePlayerAndSave(lastWeekStats, thisWeekStats, week);

	}

	private void findSamePlayerAndSave(List<PlayerStat> lastWeekStats, List<PlayerStat> thisWeekStats, int week) {
		for (PlayerStat lastWeekPlayerStat : lastWeekStats) {
			for (int i = 0; i < thisWeekStats.size(); i++) {
				String lastWeekPlayerId = lastWeekPlayerStat.getPlayerId();
				String thisWeekPlayerId = thisWeekStats.get(i).getPlayerId();
				int lastWeekGamePlay = lastWeekPlayerStat.getGamePlay();
				int thisWeekGamePlay = thisWeekStats.get(i).getGamePlay();
				if (lastWeekPlayerId.equals(thisWeekPlayerId)) {
					if (thisWeekGamePlay > lastWeekGamePlay) {
						// 같은 거 찾음
						PlayerStatWeekly playerStatWeekly = playerStatWeeklyParser
								.getPlayerStatWeekly(lastWeekPlayerStat, thisWeekStats.get(i));
						playerStatWeekly.setWeek(week);
						playerStatWeeklyRepository.save(playerStatWeekly);

						break;
					} else {
						// 경기 참가 없음
						// 지난주거 그대로 저장
					}

				}

				// thisWeekStats.remove(i);
				// break;
			}
		}
	}

	@Override
	public void savePlayerStatFromLocalFiles() {
//		int version = 202401001;
		List<LocalFileInfo> localFileInfoList = localFileReaderInterface.readPlayerStatsHtmlFiles();
		for (LocalFileInfo localFileInfo : localFileInfoList) {
			try {
				TeamURLTag teamTag = findTeamTag(localFileInfo.getFileName());
				List<PlayerStat> playerStatList = playerHtmlParser.getPlayerStatFromTeamPageHtmlString(localFileInfo.getFileContent());
				playerStatList.forEach(playerStat -> playerStat.setVersion(20241001));
				String teamNameAcronyms = teamTag.toString().substring(0,3);
				// 팀별 가져온 스탯 저장
				savePlayerStatFromTeamPage(playerStatList, teamNameAcronyms, 0);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private TeamURLTag findTeamTag(String fileName) {
		return TeamURLTag.valueOf(fileName.replace("_Stats.html", ""));
	}

}
