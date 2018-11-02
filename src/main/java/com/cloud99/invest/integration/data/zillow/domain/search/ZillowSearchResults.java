package com.cloud99.invest.integration.data.zillow.domain.search;

import com.cloud99.invest.integration.data.zillow.domain.ZillowApiResult;
import com.cloud99.invest.integration.data.zillow.domain.ZillowMessage;
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
public class ZillowSearchResults implements ZillowApiResult {

	@Getter
	@Setter
	private String schemaLocation;

	@Getter
	@Setter
	private ZillowMessage message;

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
