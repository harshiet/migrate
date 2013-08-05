package com.kaanha.migrate.core.persistence.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Persistable {
	@Id
	@GeneratedValue
	private int id;

	public int getId() {
		return id;
	}
}
