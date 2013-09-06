package com.kannha.migrate.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.swift.common.soap.jira.RemoteAuthenticationException;

@Service
public class ConnectAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean connect(MigrationRequest migrationRequest,
			MessageContext messageContext) {

		boolean success = true;
		try {
			migrationService.connectSource(migrationRequest);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			success = false;
			messageContext.addMessage(new MessageBuilder().error()
					.defaultText(e.getMessage()).build());
		}
		try {
			migrationService.connectTarget(migrationRequest);
		} catch (RemoteAuthenticationException e) {
			logger.error(e.getMessage(), e);
			success = false;
			messageContext.addMessage(new MessageBuilder().error()
					.defaultText("Invalid username and password.").build());
		} catch (Exception e) {
			if (e.getMessage() != null) {
				logger.error(e.getMessage(), e);
				success = false;
				messageContext.addMessage(new MessageBuilder().error()
						.defaultText(e.getMessage()).build());
			}
		}
		return success;

	}
}
