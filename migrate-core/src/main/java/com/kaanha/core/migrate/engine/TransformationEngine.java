package com.kaanha.core.migrate.engine;

import com.google.gson.JsonObject;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public abstract class TransformationEngine {
	SystemX source, target;

	public TransformationEngine(SystemX source, SystemX target) {
		this.source = source;
		this.target = target;
	}

	public abstract JsonObject transform(JsonObject sourceObject, ArtifactType artifactType);
}
