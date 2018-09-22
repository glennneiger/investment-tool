package com.cloud99.invest.integration.zillow.results.comps;

import com.cloud99.invest.integration.zillow.results.Message;
import com.cloud99.invest.integration.zillow.results.comps.ZillowRequest;
import com.cloud99.invest.integration.zillow.results.comps.ZillowResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the "root" call for zillow comparables xml api call
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowComparables {

	@Getter
	@Setter
	@JsonProperty("request")
	private ZillowRequest request;

	@Getter
	@Setter
	private Message message;

	@Getter
	@Setter
	@JsonProperty("response")
	private ZillowResponse response;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
