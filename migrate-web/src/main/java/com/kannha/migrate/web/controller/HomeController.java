package com.kannha.migrate.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "form" })
public class HomeController {
	
	@ModelAttribute("form")
	public MigrationRequest createForm(HttpServletRequest request) {
		MigrationRequest migrationRequest = new MigrationRequest();
		return migrationRequest;
	}

	@RequestMapping("/home.do")
	public void doHome() {
	}

}
