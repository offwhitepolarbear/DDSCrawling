package com.kihwangkwon.apidata.player;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.kihwangkwon.common.GetHTMLByJsoup;

@Service
public class PlayerApi {
	String urlHead = "https://dds-4cda3.firebaseapp.com/";
	
	public Document getPlayerIndex() {
		String url = urlHead + "PlayerIndex.html";
		return GetHTMLByJsoup.getDocument(url);
	}
	
	public Document getPlayerStatByTeamPage(String teamName) {
		String url = urlHead+teamName+"_Stats.html";
		return GetHTMLByJsoup.getDocument(url);
	}
	
	public Document getPlayerRatingFromTeamPage(String teamName) {
		String url = urlHead+teamName+"_Ratings.html";
		return GetHTMLByJsoup.getDocument(url);
	}
	
}
