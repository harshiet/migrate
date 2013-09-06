package com.kaanha.core.migrate.metadata;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.web.client.RestClientException;

import com.google.gson.JsonObject;

public class JIRAMetdataReaderTest extends TestCase {

	@Test
	public void testReadProjectMetadata() throws ServiceException {

		JIRAMetadataReader jiraMetadataReader;
		try {
			jiraMetadataReader = new JIRAMetadataReader(
					"https://kaanha.atlassian.net", "admin",
					"admin123");
			JsonObject metadata = jiraMetadataReader.readProjectMetadata();
			System.out.println(metadata);
		} catch (RestClientException | URISyntaxException | IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testReadUserStoryMetadata() {

	}

}
