package com.kaanha.migrate.core.domain.rally;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class QueryResult<T extends DomainObject> {

	@JsonProperty("TotalResultCount")
	private Long totalResultCount;
	@JsonProperty("StartIndex")
	private Long startIndex;
	@JsonProperty("PageSize")
	private Long pageSize;
	@JsonProperty("_rallyAPIMajor")
	private Long rallyAPIMajor;
	@JsonProperty("_rallyAPIMinor")
	private Long rallyAPIMinor;
	@JsonProperty("Errors")
	private List<String> errors;
	@JsonProperty("Warnings")
	private List<String> warnings;
	@JsonProperty("Results")
	private List<T> results;

	public Long getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(Long value) {
		this.totalResultCount = value;
	}

	public Long getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Long value) {
		this.startIndex = value;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long value) {
		this.pageSize = value;
	}

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

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
