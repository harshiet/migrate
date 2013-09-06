package com.kannha.migrate.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

@Service
public class FetchMetadataAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean fetch(MigrationRequest migrationRequest,
			MessageContext messageContext, RequestContext requestContext) {

		boolean success = true;
		logger.debug(requestContext.getModel().get("rally"));
		logger.debug(requestContext.getModel().get("jira"));
		return success;

	}
}
