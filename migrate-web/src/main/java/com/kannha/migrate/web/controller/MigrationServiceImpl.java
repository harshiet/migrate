package com.kannha.migrate.web.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("migrationService")
public class MigrationServiceImpl implements MigrationService {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean valiateConnection(MigrationRequest migrationRequest) {
		logger.debug("valiateConnection");
		return false;
	}

}
