package com.kaanha.core.migrate.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.SystemArtifactAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class RallyToJiraTransformationEngine extends TransformationEngine {
	private static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public RallyToJiraTransformationEngine(SystemX source, SystemX target) {
		super(source, target);
	}

	public JsonObject transform(JsonObject sourceObject, ArtifactType artifactType) {
		System.out.println("Transform: " + sourceObject);

		switch (artifactType) {
		case PROJECT: {
			return toProject(sourceObject);
		}
		default: {
			return null;
		}
		}
	}

	private JsonObject toProject(JsonObject sourceObject) {
		String key = "PROJ";
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		JsonObject json = mapValues(sourceObject, map);
		return json;
	}

	private JsonObject mapValues(JsonObject sourceObject, Map<String, String> map) {
		List<SystemArtifactAttribute> attributes = target.getArtifactofType(ArtifactType.PROJECT).getAttributes();
		for (SystemArtifactAttribute attribute : attributes) {
			SystemArtifactAttribute mappedAttribute = attribute.getAttributeMapping(source);
			String attributeMapping = mappedAttribute.getName();
			JsonElement attributeValue = sourceObject.get(attributeMapping);

			while (mappedAttribute.hasChild()) {
				if (attributeValue == null) {
					logger.warning("Null " + attributeMapping);
					break;
				}
				if (attributeValue.isJsonNull()) {
					break;
				}
				mappedAttribute = mappedAttribute.getChildAttribute();
				attributeMapping = mappedAttribute.getName();
				attributeValue = attributeValue.getAsJsonObject().get(attributeMapping);
			}
			if (attributeValue != null && StringUtils.isNotBlank(attributeValue.getAsString())) {
				map.put(attribute.getName(), attributeValue.getAsString());
			}
		}

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		JsonObject json = (JsonObject) (new JsonParser()).parse(gson.toJson(map));
		return json;
	}
}
