package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.kaanha.migrate.core.api.rest.RallyRestApi;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;

public class RallyMetadataReader extends RallyRestApi {

	public RallyMetadataReader(String url, String username, String password) throws URISyntaxException {
		super(url, username, password);

	}

	public JsonArray readProjectMetadata() throws URISyntaxException, IOException {
		return readMetadata("Project");
	}

	private JsonArray readMetadata(String workspaceObjectName) throws IOException {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Name", workspaceObjectName);
		return searchObjects(ArtifactType.TYPE_DEFINITION, filter);

	}

}
