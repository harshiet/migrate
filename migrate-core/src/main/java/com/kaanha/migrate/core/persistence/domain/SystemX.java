package com.kaanha.migrate.core.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "version" }))
@Entity(name = "system")
public class SystemX extends Persistable {

	@Column(columnDefinition = "TEXT")
	String name;

	@Column(columnDefinition = "TEXT")
	String version;

	@OneToMany(mappedBy = "system", cascade = CascadeType.ALL, orphanRemoval = true)
	List<SystemArtifact> artifacts = new ArrayList<SystemArtifact>();

	public SystemX() {
		super();
	}

	public SystemX(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<SystemArtifact> getArtifacts() {
		return artifacts;
	}

	public SystemArtifact addArtifact(ArtifactType artifact, String name) {
		SystemArtifact systemArtifact = new SystemArtifact();
		systemArtifact.setArtifactType(artifact);
		systemArtifact.setName(name);
		systemArtifact.setSystem(this);
		artifacts.add(systemArtifact);
		return systemArtifact;
	}

	public SystemArtifact getArtifactofType(ArtifactType artifactType) {
		for (SystemArtifact systemArtifact : getArtifacts()) {
			if (systemArtifact.getArtifactType().equals(artifactType)) {
				return systemArtifact;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemX other = (SystemX) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}