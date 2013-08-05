package com.kaanha.migrate.core.domain.rally;

import org.codehaus.jackson.annotate.JsonProperty;

public class QueryResults<T extends DomainObject> {

	@JsonProperty("QueryResult")
	private QueryResult<T> queryResult;

	public QueryResult<T> getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(QueryResult<T> queryResult) {
		this.queryResult = queryResult;
	}

}
