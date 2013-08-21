package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.JsonObject;

public class RallyMetdataReaderTest extends TestCase {

	@Test
	public void testReadProjectMetadata() {

		RallyMetadataReader rallyMetadataReader;
		try {
			rallyMetadataReader = new RallyMetadataReader("https://rally1.rallydev.com/slm/webservice/v2.0/", "rally.user.2@gmail.com", "RallyUser123!");
			JsonObject projectMetadata = rallyMetadataReader.readProjectMetadata();
			assertEquals("Project", projectMetadata.get("name").getAsString());
			assertTrue(projectMetadata.get("attributes").getAsJsonArray().size() > 0);
			
		} catch (URISyntaxException | IOException e) {
			fail(e.getMessage());
		}

	}

}
