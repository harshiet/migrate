package com.kannha.migrate.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;

@Service
public class ConnectAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());
	public boolean connect(MigrationRequest migrationRequest, MessageContext messageContext) {

		boolean success = true;
		try {
			migrationService.connectSource(migrationRequest);
		} catch (Exception e) {
			success = false;
			messageContext.addMessage(new MessageBuilder().error().defaultText(e.getMessage()).build());
		}
		try {
			migrationService.connectTarget(migrationRequest);
		} catch (Exception e) {
			success = false;
			messageContext.addMessage(new MessageBuilder().error().defaultText(e.getMessage()).build());
		}
		return success;

	}
}
