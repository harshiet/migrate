package com.kaanha.migrate.core.domain.rally;

import org.codehaus.jackson.annotate.JsonProperty;

public class FetchResult<T extends DomainObject> {

	@JsonProperty("Project")
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
