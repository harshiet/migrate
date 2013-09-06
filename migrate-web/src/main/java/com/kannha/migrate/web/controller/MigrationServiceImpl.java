package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kaanha.core.migrate.metadata.JIRAMetadataReader;
import com.kaanha.migrate.core.api.rest.RallyReadApi;

@Service("migrationService")
public class MigrationServiceImpl implements MigrationService {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public void connectSource(MigrationRequest migrationRequest) throws URISyntaxException, IOException {
		logger.debug("connectSource");
		RallyReadApi rallyReadApi = new RallyReadApi(migrationRequest.getSourceUrl(), migrationRequest.getSourceUsername(), migrationRequest.getSourcePassword());
	}

	public void connectTarget(MigrationRequest migrationRequest) throws URISyntaxException {
		logger.debug("connectTarget");
		JIRAMetadataReader jiraMetadataReader = new JIRAMetadataReader(migrationRequest.getTargetUrl(), migrationRequest.getTargetUsername(), migrationRequest.getTargetPassword());
	}

}
