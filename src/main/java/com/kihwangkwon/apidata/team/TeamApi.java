package com.kihwangkwon.apidata.team;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.team.domain.TeamInfo;
import com.kihwangkwon.businesslogic.team.domain.TeamStat;
import com.kihwangkwon.businesslogic.team.domain.TeamTag;
import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;
import com.kihwangkwon.common.GetDataFromExternalApi;
import com.kihwangkwon.common.GetHTMLByJsoup;
import com.kihwangkwon.common.URLMaker;

@Service
public class TeamApi {
	
	private URLMaker uRLMaker;
	
	private GetDataFromExternalApi getDataFromExternalApi;
	
	@Autowired
	public TeamApi(URLMaker uRLMaker, GetDataFromExternalApi getDataFromExternalApi) {
		this.uRLMaker = uRLMaker;
		this.getDataFromExternalApi = getDataFromExternalApi;
	}
	
	public String getTeamInfoFromApi(TeamTag teamTag) {
		TeamURLTag teamURL = teamTagToTeamURLTag(teamTag);
		String url = uRLMaker.getTeamStatURL(teamURL);
		Document doc = GetHTMLByJsoup.getDocument(url);
		tester(doc);
		//doc.select("table.thead");
		return GetDataFromExternalApi.getDataFromApi(url);
	}
	
	private TeamURLTag teamTagToTeamURLTag(TeamTag teamTag) {
		TeamURLTag result = null;
		TeamURLTag[] urls = TeamURLTag.values();
		for(TeamURLTag teamURL:urls) {
			String url = teamURL.toString();
			if(url.startsWith(teamTag.toString())) {
				result = teamURL;
			}
		}
		return result;
	}
	
	private void tester(Document doc) {
		
		System.out.println("테스트");
		Iterator<Element> test1= doc.select("div.label").iterator();		
		while(test1.hasNext()) {
			System.out.println(test1.next().parent().text());
			if(test1.next().text().equals("Rebounds Per Game")) {
				System.out.println(test1.next().parent().text());
			}
			//System.out.println("ㄱㄱ"+test1.next().);
		}
		System.out.println("테스트");	
	}
	
	private TeamStat getTeamInfoFromHtml(String html) {
		TeamStat teamStat = null;
		int pointStart = html.lastIndexOf("Points Per Game");
		int pointEnd = html.lastIndexOf("Assists Per Game");
		//teamInfo = TeamInfo.builder().
				
		return teamStat;
	}
	
	private double getPoint(String html) {
		double result = 0;
		int pointStart = html.lastIndexOf("Points Per Game");
		int pointEnd = html.lastIndexOf("Assists Per Game");
		String pointString = html.substring(pointStart, pointEnd);
		result = Double.parseDouble(pointString);
		return result;
	}
	
	private Document getGameResult(int gameNumber) {
		
		return null;
	}
	
}
