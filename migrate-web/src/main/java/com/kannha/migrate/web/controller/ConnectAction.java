package com.kannha.migrate.web.controller;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

public class ConnectAction {

	MigrationService migrationService;

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
