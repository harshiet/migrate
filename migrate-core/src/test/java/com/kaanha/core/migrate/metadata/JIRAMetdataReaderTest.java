package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.JsonObject;

public class JIRAMetdataReaderTest extends TestCase {

	@Test
	public void testReadProjectMetadata() {

		JIRAMetadataReader jiraMetadataReader;
		try {
			jiraMetadataReader = new JIRAMetadataReader("https://kaanha.atlassian.net", "rally.user.2@gmail.com", "RallyUser123!");
			JsonObject metadata = jiraMetadataReader.readProjectMetadata();
			System.out.println(metadata);
		} catch (URISyntaxException | IOException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testReadUserStoryMetadata() {

	}

}
