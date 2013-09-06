package com.kannha.migrate.web.controller;

import java.io.Serializable;

public class MigrationRequest implements Serializable {

	private static final long serialVersionUID = -2452095704500897670L;
	private String sourceUrl = "https://rally1.rallydev.com/slm/webservice/v2.0/";
	private String sourceUsername = "rally.user.2@gmail.com";
	private String sourcePassword = "RallyUser123!";
	private String targetUrl = "https://kaanha.atlassian.net";
	private String targetUsername = "admin";
	private String targetPassword = "admin123";

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceUsername() {
		return sourceUsername;
	}

	public void setSourceUsername(String sourceUsername) {
		this.sourceUsername = sourceUsername;
	}

	public String getSourcePassword() {
		return sourcePassword;
	}

	public void setSourcePassword(String sourcePassword) {
		this.sourcePassword = sourcePassword;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetUsername() {
		return targetUsername;
	}

	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}

	public String getTargetPassword() {
		return targetPassword;
	}

	public void setTargetPassword(String targetPassword) {
		this.targetPassword = targetPassword;
	}

}
