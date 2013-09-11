package com.kannha.migrate.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.swift.common.soap.jira.RemoteAuthenticationException;

import com.kaanha.migrate.core.api.JiraApi;
import com.kaanha.migrate.core.api.RallyApi;

@Service
public class ConnectAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public Object connectSource(MigrationRequest migrationRequest,
			MessageContext messageContext) {

		RallyApi rally = null;
		try {
			rally = migrationService.connectSource(migrationRequest);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			messageContext.addMessage(new MessageBuilder().error()
					.defaultText(e.getMessage()).build());
			return false;
		}
		return rally;

	}

	public Object connectTarget(MigrationRequest migrationRequest,
			MessageContext messageContext) {
		JiraApi jira = null;
		try {
			jira = migrationService.connectTarget(migrationRequest);

		} catch (RemoteAuthenticationException e) {
			logger.error(e.getMessage(), e);
			messageContext.addMessage(new MessageBuilder().error()
					.defaultText("Invalid username and password.").build());
			return false;
		} catch (Exception e) {
			if (e.getMessage() != null) {
				logger.error(e.getMessage(), e);
				messageContext.addMessage(new MessageBuilder().error()
						.defaultText(e.getMessage()).build());
			}
			return false;
		}
		return jira;

	}
}
