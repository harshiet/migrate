package com.kaanha.migrate.core;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.junit.Test;

import com.kaanha.migrate.core.persistence.domain.ArtifactType;
import com.kaanha.migrate.core.persistence.domain.AttributeType;
import com.kaanha.migrate.core.persistence.domain.SystemAttribute;
import com.kaanha.migrate.core.persistence.domain.SystemX;

public class PersistenceTest {

	private static EntityManager em;

	@BeforeClass
	public static void setupEm() {
		try {
			em = Persistence.createEntityManagerFactory("migrate_test").createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSystemArtifact() {
		em.getTransaction().begin();

		SystemX systemRally = new SystemX("Rally", "Community");
		// SystemX systemJira = new SystemX("Jira", "OnDemand");

		systemRally.addAttribute(ArtifactType.SUBSCRIPTION, AttributeType.NAME,"Subscription");

		em.persist(systemRally);
		// em.persist(systemJira);
		em.getTransaction().commit();
	}

	@Test
	public void testSearch() {
		Query q = em
				.createQuery("select s from com.kaanha.migrate.core.persistence.domain.SystemX s where s.name = :name");
		q.setParameter("name", "Rally");
		try {
			SystemX system = (SystemX) q.getSingleResult();
			assert (system.getAttributes().size() > 0);
			for (SystemAttribute attribute : system.getAttributes()) {
				System.out.println(attribute);
			}
		} catch (NoResultException nre) {
			fail(nre.getMessage());
		}

	}

}
