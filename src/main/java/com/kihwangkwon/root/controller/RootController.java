package com.kihwangkwon.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
	@RequestMapping("/")
	public String main() {
		System.out.println("ggdd");
		return "index.html";
	}
}
