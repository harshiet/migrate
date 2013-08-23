package com.kaanha.migrate.core.persistence.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "SYSTEM_ID", referencedColumnName = "ID")
	List<SystemAttribute> attributes = new ArrayList<SystemAttribute>();

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

	public void addAttribute(ArtifactType artifact, AttributeType attribute, String name) {
		if (artifact != null) {
			attributes.add(new SystemAttribute(artifact, attribute, name));
		}
	}

	public List<SystemAttribute> getAttributes() {
		return attributes;
	}

	public Map<AttributeType, String> getAttributesFor(ArtifactType artifactType) {
		Map<AttributeType, String> attributesMap = new HashMap<AttributeType, String>();
		for(SystemAttribute attribute : attributes){
			if(attribute.getArtifact().equals(artifactType)){
				attributesMap.put(attribute.getAttribute(), attribute.getName());
			}
		}
		return null;
	}
}