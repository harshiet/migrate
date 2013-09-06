package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.springframework.web.client.RestClientException;
import org.swift.common.soap.jira.RemoteAuthenticationException;
import org.swift.common.soap.jira.RemoteException;


public interface MigrationService {

	void connectSource(MigrationRequest migrationRequest) throws URISyntaxException, IOException;
	void connectTarget(MigrationRequest migrationRequest) throws URISyntaxException, RemoteAuthenticationException, RemoteException, RestClientException, java.rmi.RemoteException, ServiceException;
}
