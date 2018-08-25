package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * This object hierarchy represents the results from a zillow search.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowSearchResults {

	@Getter
	@Setter
	private String schemaLocation;

	@Getter
	@Setter
	private Message message;

	@Getter
	@Setter
	@JsonProperty("response")
	private ZillowResponse response;

	@Getter
	@Setter
	@JsonProperty("request")
	private ZillowRequest request;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
