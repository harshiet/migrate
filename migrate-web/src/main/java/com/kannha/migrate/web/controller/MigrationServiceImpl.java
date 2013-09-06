package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.swift.common.soap.jira.RemoteAuthenticationException;
import org.swift.common.soap.jira.RemoteException;

import com.kaanha.migrate.core.api.JiraApi;
import com.kaanha.migrate.core.api.RallyApi;
import com.kaanha.migrate.core.api.RallyReadApi;

@Service("migrationService")
public class MigrationServiceImpl implements MigrationService {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public RallyApi connectSource(MigrationRequest migrationRequest)
			throws URISyntaxException, IOException {
		logger.debug("connectSource");
		RallyReadApi rallyReadApi = new RallyReadApi(
				migrationRequest.getSourceUrl(),
				migrationRequest.getSourceUsername(),
				migrationRequest.getSourcePassword());
		return rallyReadApi;

	}

	public JiraApi connectTarget(MigrationRequest migrationRequest)
			throws URISyntaxException, RemoteAuthenticationException,
			RemoteException, RestClientException, java.rmi.RemoteException,
			ServiceException {
		logger.debug("connectTarget");
		JiraApi jira = new JiraApi(migrationRequest.getTargetUrl(),
				migrationRequest.getTargetUsername(),
				migrationRequest.getTargetPassword());
		return jira;
	}

}
