package com.kihwangkwon.businesslogic.team.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kihwangkwon.apidata.team.TeamApi;
import com.kihwangkwon.businesslogic.team.domain.TeamInfo;
import com.kihwangkwon.businesslogic.team.domain.TeamTag;

@Service
public class TeamServiceImpl implements TeamService {
	
	private TeamApi teamApi;
	
	@Autowired
	public TeamServiceImpl(TeamApi teamApi) {
		this.teamApi = teamApi;
	}

	@Override
	public TeamInfo getTeamInfo(String teamTag) {
		TeamTag targetTeamTag = TeamTag.valueOf(teamTag);
		String result = teamApi.getTeamInfoFromApi(targetTeamTag);
		System.out.println(result);
		return null;
	}
	
	
}
