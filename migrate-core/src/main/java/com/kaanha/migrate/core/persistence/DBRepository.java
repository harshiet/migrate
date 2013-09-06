package com.kaanha.migrate.core.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.kaanha.migrate.core.persistence.domain.QSystemX;
import com.kaanha.migrate.core.persistence.domain.SystemX;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

public class DBRepository {

	private EntityManager em;
	private static DBRepository dbRepository;

	private DBRepository(EntityManager em) {
		this.em = em;
	}

	public SystemX findSystemByName(String name) {
		JPQLQuery query = new JPAQuery(em);
		QSystemX systemX = QSystemX.systemX;
		SystemX system = query.from(systemX).where(systemX.name.eq(name)).uniqueResult(systemX);
		return system;
	}

	public static DBRepository getInstance() {
		if (dbRepository == null) {
			EntityManager em = Persistence.createEntityManagerFactory("migrate").createEntityManager();
			dbRepository = new DBRepository(em);
		}
		return dbRepository;
	}

	// public Artifact getArtifactByName(String code) {
	// JPQLQuery query = new JPAQuery(em);
	// QArtifact qArtifact =QArtifact.artifact;
	// Artifact artifact=
	// query.from(qArtifact).where(qArtifact.code.eq(code)).uniqueResult(qArtifact);
	// return artifact;
	// }

}
