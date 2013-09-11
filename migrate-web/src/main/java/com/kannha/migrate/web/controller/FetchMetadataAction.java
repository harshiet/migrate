package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.kaanha.core.migrate.metadata.RallyMetadataReader;
import com.kaanha.migrate.core.api.RallyApi;

@Service
public class FetchMetadataAction {

	@Autowired
	MigrationService migrationService;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean fetch(MigrationRequest migrationRequest,
			MessageContext messageContext, Object system)
			throws URISyntaxException, IOException {

		boolean success = true;
		logger.debug(system);
		RallyApi rally = (RallyApi) system;
		RallyMetadataReader rallyMetadataReader = new RallyMetadataReader(rally);
		JsonObject rallyProjectMetdata = rallyMetadataReader
				.readProjectMetadata();
		return success;

	}
}
