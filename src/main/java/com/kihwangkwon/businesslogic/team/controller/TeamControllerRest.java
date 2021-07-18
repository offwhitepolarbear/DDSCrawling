package com.kihwangkwon.businesslogic.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kihwangkwon.businesslogic.team.service.TeamService;

@RequestMapping("/rest/team")
@RestController
public class TeamControllerRest {
	
	private TeamService teamService;
	
	@Autowired
	public TeamControllerRest(TeamService teamService) {
		this.teamService = teamService;
	}
	
	@RequestMapping("/{teamTag}")
	public String getTeam(@PathVariable("teamTag") String teamTag) {
		
		System.out.println(teamTag);
		teamService.getTeamInfo(teamTag);
		return null;
	}
}
