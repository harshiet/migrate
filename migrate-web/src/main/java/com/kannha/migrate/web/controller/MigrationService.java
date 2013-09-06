package com.kannha.migrate.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;


public interface MigrationService {

	void connectSource(MigrationRequest migrationRequest) throws URISyntaxException, IOException;
	void connectTarget(MigrationRequest migrationRequest) throws URISyntaxException;
}
