package com.kaanha.migrate.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.AttributeMapping;
import com.kaanha.migrate.core.persistence.domain.SystemArtifact;
import com.kaanha.migrate.core.persistence.domain.SystemArtifactAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class PersistenceTest {

	private static EntityManager em;

	@BeforeClass
	public static void setupEm() {
		em = Persistence.createEntityManagerFactory("migrate_test").createEntityManager();
	}

	@Test
	public void testSystemArtifact() {
		em.getTransaction().begin();

		SystemX systemRally = new SystemX("Rally", "Community");
		SystemX systemJira = new SystemX("Jira", "OnDemand");

		addArtifactWithoutAttributeMappings(ArtifactType.SUBSCRIPTION, systemRally, "subscription",
				Arrays.asList(new String[] { "Workspaces" }));
		addArtifactWithoutAttributeMappings(ArtifactType.WORKSAPCE, systemRally, "workspace",
				Arrays.asList(new String[] { "Projects" }));

		Map<String, String> projectMappings = new HashMap<String, String>();
		projectMappings.put("Name", "title");
		projectMappings.put("Owner:_refObjectName", "admin");
		addArtifactWithAttributeMappings(ArtifactType.PROJECT, systemRally, systemJira, "project", "Project",
				projectMappings, Arrays.asList(new String[] { "objectid", "iterations" }), null);

		addArtifactWithoutAttributeMappings(ArtifactType.USER_STORY, systemRally, "HierarchicalRequirement",
				Arrays.asList(new String[] { "attachments", "iteration", "tasks", "defects" }));

		addArtifactWithoutAttributeMappings(ArtifactType.USER_STORY, systemJira, "Issue",
				Arrays.asList(new String[] { "summary" }));

		em.persist(systemRally);
		em.persist(systemJira);
		em.getTransaction().commit();
	}

	private SystemArtifact addArtifactWithoutAttributeMappings(ArtifactType artifactType, SystemX system,
			String artifactName, List<String> attributes) {
		SystemArtifact artifact = system.addArtifact(artifactType, artifactName);
		addAttributesToArtifact(artifact, attributes);
		return artifact;

	}

	private void addArtifactWithAttributeMappings(ArtifactType artifactType, SystemX source, SystemX target,
			String artifactNameSource, String artifactNameTarget, Map<String, String> attributeMappings,
			List<String> unmappedSourceAttributes, List<String> unmappedTargetAttributes) {

		SystemArtifact sourceArtifact = addArtifactWithoutAttributeMappings(artifactType, source, artifactNameSource,
				unmappedSourceAttributes);
		SystemArtifact targetArtifact = addArtifactWithoutAttributeMappings(artifactType, target, artifactNameTarget,
				unmappedTargetAttributes);

		if (attributeMappings != null) {
			for (String sourceAttributeName : attributeMappings.keySet()) {
				String sourceChildAttributeName = "";
				if (sourceAttributeName.contains(":")) {
					sourceChildAttributeName = sourceAttributeName.split(":")[1];
					sourceAttributeName = sourceAttributeName.split(":")[0];
				}
				SystemArtifactAttribute sourceAttribute = sourceArtifact.addAttribute(sourceAttributeName);
				if (StringUtils.isNotBlank(sourceChildAttributeName)) {
					sourceAttribute.withChildAttribute(sourceChildAttributeName);
				}
				String targetAttributeName = attributeMappings.get(sourceAttributeName);
				SystemArtifactAttribute targetAttribute = targetArtifact.addAttribute(targetAttributeName);
				sourceAttribute.addMapping(targetAttribute);
			}
		}
	}

	private void addAttributesToArtifact(SystemArtifact artifact, List<String> attributes) {
		if (artifact != null && attributes != null) {
			for (String attributeName : attributes) {
				artifact.addAttribute(attributeName);
			}
		}
	}

	@Test
	public void testSearch() {
		Query q = em
				.createQuery("select s from com.kaanha.migrate.core.persistence.domain.SystemX s where s.name = :name");
		q.setParameter("name", "Rally");
		try {
			SystemX system = (SystemX) q.getSingleResult();
			assertTrue(system.getArtifacts().size() > 0);
			for (SystemArtifact artifact : system.getArtifacts()) {
				System.out.println(artifact.getName());
				for (SystemArtifactAttribute attribute : artifact.getAttributes()) {
					System.out.println(attribute.getName());
					for (AttributeMapping mapping : attribute.getAttributeMappings()) {
						System.out.println(mapping.getMappedAttribute().getArtifact().getSystem().getName() + " - "
								+ mapping.getMappedAttribute().getName());
					}
				}

			}
		} catch (NoResultException nre) {
			fail(nre.getMessage());
		}

	}

}
