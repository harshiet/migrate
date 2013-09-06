package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.kaanha.migrate.core.api.RallyApi;

public class RallyMetdataReaderTest extends TestCase {

	@Test
	public void testReadProjectMetadata() {

		RallyMetadataReader rallyMetadataReader;
		try {
			rallyMetadataReader = new RallyMetadataReader(new RallyApi(
					"https://rally1.rallydev.com/slm/webservice/v2.0/",
					"rally.user.2@gmail.com", "RallyUser123!"));
			JsonObject metadata = rallyMetadataReader.readProjectMetadata();
			assertEquals("Project", metadata.get("name").getAsString());
			assertTrue(metadata.get("primitives").getAsJsonArray().size() > 0);
			System.out.println(metadata);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testReadUserStoryMetadata() {

		RallyMetadataReader rallyMetadataReader;
		try {
			rallyMetadataReader = new RallyMetadataReader(new RallyApi(
					"https://rally1.rallydev.com/slm/webservice/v2.0/",
					"rally.user.2@gmail.com", "RallyUser123!"));
			JsonObject metadata = rallyMetadataReader.readUserStoryMetadata();
			assertEquals("Hierarchical Requirement", metadata.get("name")
					.getAsString());
			assertTrue(metadata.get("primitives").getAsJsonArray().size() > 0);
			System.out.println(metadata);
		} catch (URISyntaxException | IOException e) {
			fail(e.getMessage());
		}

	}

}
