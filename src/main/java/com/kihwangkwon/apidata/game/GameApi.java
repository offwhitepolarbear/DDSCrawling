package com.kihwangkwon.apidata.game;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.kihwangkwon.common.GetHTMLByJsoup;
import com.kihwangkwon.common.domain.BaseUrl;
@Service
public class GameApi {
	
	String urlHead = BaseUrl.baseUrl;
	
	public Document getGameInfo(String gameId) {
		String url = urlHead+"Game"+gameId+".html";
		return GetHTMLByJsoup.getDocument(url);
	}
	
	public Document getGameList(String teamName) {
		String url = urlHead+teamName+"_Schedule.html";
		return GetHTMLByJsoup.getDocument(url);
	}
}
