package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.springframework.web.client.RestClientException;
import org.swift.common.soap.jira.RemoteAuthenticationException;
import org.swift.common.soap.jira.RemoteException;

import com.kaanha.migrate.core.api.JiraApi;
import com.kaanha.migrate.core.api.RallyApi;

public interface MigrationService {

	RallyApi connectSource(MigrationRequest migrationRequest)
			throws URISyntaxException, IOException;

	JiraApi connectTarget(MigrationRequest migrationRequest)
			throws URISyntaxException, RemoteAuthenticationException,
			RemoteException, RestClientException, java.rmi.RemoteException,
			ServiceException;
}
