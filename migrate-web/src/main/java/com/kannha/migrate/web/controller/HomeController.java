package com.kannha.migrate.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "form" })
public class HomeController {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@ModelAttribute("form")
	public MigrationRequest createForm(HttpServletRequest request) {
		MigrationRequest migrationRequest = new MigrationRequest();
		return migrationRequest;
	}

	@RequestMapping("/homes.do")
	public void doHome() {
		logger.debug("doHome");
		
	}

}
