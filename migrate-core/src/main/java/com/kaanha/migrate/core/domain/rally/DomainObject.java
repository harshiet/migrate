package com.kaanha.migrate.core.domain.rally;

import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.annotate.JsonProperty;

public class DomainObject {
	@JsonProperty("_rallyAPIMajor")
	private Long rallyAPIMajor;
	@JsonProperty("_rallyAPIMinor")
	private Long rallyAPIMinor;
	@JsonProperty("_ref")
	private String ref;
	@JsonProperty("_refObjectName")
	private String refObjectName;
	@JsonProperty("_type")
	private String type;
	@JsonProperty("_objectVersion")
	private Long objectVersion;
	@JsonProperty("CreationDate")
	private XMLGregorianCalendar creationDate;
	@JsonProperty("_CreatedAt")
	private String CreatedAt;
	@JsonProperty("ObjectID")
	private Long ObjectID;

	public Long getRallyAPIMajor() {
		return rallyAPIMajor;
	}

	public void setRallyAPIMajor(Long rallyAPIMajor) {
		this.rallyAPIMajor = rallyAPIMajor;
	}

	public Long getRallyAPIMinor() {
		return rallyAPIMinor;
	}

	public void setRallyAPIMinor(Long rallyAPIMinor) {
		this.rallyAPIMinor = rallyAPIMinor;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRefObjectName() {
		return refObjectName;
	}

	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(Long objectVersion) {
		this.objectVersion = objectVersion;
	}

	public XMLGregorianCalendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(XMLGregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}

}
