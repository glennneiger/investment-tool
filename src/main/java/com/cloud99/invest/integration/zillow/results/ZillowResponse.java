package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowResponse {

	private ZillowResults results;

	public ZillowResults getResults() {
		return results;
	}

	public void setResults(ZillowResults results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ClassPojo [results = " + results + "]";
	}

}
