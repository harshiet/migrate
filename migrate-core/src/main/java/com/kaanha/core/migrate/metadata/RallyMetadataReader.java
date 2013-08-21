package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaanha.migrate.core.api.rest.RallyRestApi;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;

public class RallyMetadataReader extends RallyRestApi {

	public RallyMetadataReader(String url, String username, String password) throws URISyntaxException {
		super(url, username, password);

	}

	public JsonObject readProjectMetadata() throws URISyntaxException, IOException {
		return readMetadata("Project");
	}

	private JsonObject readMetadata(String workspaceObjectName) throws IOException, RestClientException, URISyntaxException {
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Name", workspaceObjectName);
		JsonArray metadataResults = searchObjects(ArtifactType.TYPE_DEFINITION, filter);
		JsonObject metadata = metadataResults.get(0).getAsJsonObject();
		JsonObject objAttributes = getObjectFromRef(metadata.get("Attributes").getAsJsonObject().get("_ref").getAsString());
		JsonArray arrAttributes = objAttributes.get("QueryResult").getAsJsonObject().get("Results").getAsJsonArray();
		JsonObject out = new JsonObject();
		out.addProperty("name", metadata.get("_refObjectName").getAsString());
		out.addProperty("displayName", metadata.get("DisplayName").getAsString());
		JsonArray attributes = new JsonArray();
		
		for(JsonElement eleAttribute: arrAttributes){
			JsonObject objAttribute = eleAttribute.getAsJsonObject();
			JsonObject attribute = new JsonObject();
			attribute.addProperty("name", objAttribute.get("ElementName").getAsString());
			attributes.add(attribute);
		}
		
		out.add("attributes", attributes);
		
		return out;
		
	}

}
