package com.kannha.migrate.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.swift.common.soap.jira.RemoteAuthenticationException;

import com.kaanha.core.migrate.metadata.RallyMetadataReader;
import com.kaanha.core.migrate.metadata.RallyMetdataReaderTest;

@Service
public class FetchMetadataAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean fetch(MigrationRequest migrationRequest,
			MessageContext messageContext) {

		boolean success = true;
		RallyMetadataReader rallyMetadataReader = new RallyMetadataReader(url, username, password)
		return success;

	}
}
