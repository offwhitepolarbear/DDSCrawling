package com.kihwangkwon.common;

import org.springframework.stereotype.Service;

import com.kihwangkwon.businesslogic.team.domain.TeamURLTag;

@Service
public class URLMaker {
	final String home = "https://dds-4cda3.firebaseapp.com/";
	
	public String getTeamStatURL(TeamURLTag teamTag) {
		return home+teamTag.toString()+"_Info.html";
	}
	
	public String getPlayerInfoURL(TeamURLTag teamTag) {
		return home+teamTag.toString()+"_Roster.html";
	}
	
	
	public String getPlayerAbilityURL(TeamURLTag teamTag) {
		return home+teamTag.toString()+"_Ratings.html";
	}
	
	public String getPlayerStatURL(TeamURLTag teamTag) {
		return home+teamTag.toString()+"_Stats.html";
	}
		
}
