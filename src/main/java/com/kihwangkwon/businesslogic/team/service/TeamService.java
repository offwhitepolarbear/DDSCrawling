package com.kihwangkwon.businesslogic.team.service;

import com.kihwangkwon.businesslogic.team.domain.TeamInfo;
import com.kihwangkwon.businesslogic.team.domain.TeamTag;

public interface TeamService {
	public TeamInfo getTeamInfo(String teamTag);
}
