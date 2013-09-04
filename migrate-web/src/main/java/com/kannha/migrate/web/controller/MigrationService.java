package com.kannha.migrate.web.controller;

import java.net.URISyntaxException;


public interface MigrationService {

	void connectSource(MigrationRequest migrationRequest) throws URISyntaxException;
	void connectTarget(MigrationRequest migrationRequest) throws URISyntaxException;
}
