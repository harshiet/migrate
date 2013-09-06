package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.swift.common.soap.jira.RemoteAuthenticationException;
import org.swift.common.soap.jira.RemoteException;

import com.kaanha.core.migrate.metadata.JIRAMetadataReader;
import com.kaanha.migrate.core.api.rest.RallyReadApi;

@Service("migrationService")
public class MigrationServiceImpl implements MigrationService {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public void connectSource(MigrationRequest migrationRequest) throws URISyntaxException, IOException {
		logger.debug("connectSource");
		RallyReadApi rallyReadApi = new RallyReadApi(migrationRequest.getSourceUrl(), migrationRequest.getSourceUsername(), migrationRequest.getSourcePassword());
	}

	public void connectTarget(MigrationRequest migrationRequest) throws URISyntaxException, RemoteAuthenticationException, RemoteException, RestClientException, java.rmi.RemoteException, ServiceException {
		logger.debug("connectTarget");
		JIRAMetadataReader jiraMetadataReader = new JIRAMetadataReader(migrationRequest.getTargetUrl(), migrationRequest.getTargetUsername(), migrationRequest.getTargetPassword());
	}

}
