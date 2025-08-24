package com.kihwangkwon.businesslogic.draft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DraftController {
	
	@RequestMapping("/draft/{season}/{team}/{password}")
	public String draftMain(@PathVariable("season") String season, 
			@PathVariable("team") String team, 
			@PathVariable("password") String password) {
		return "draft/draft";
	}
	
}
