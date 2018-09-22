package com.cloud99.invest.integration.zillow.results.comps;

import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {

	@Getter
	@Setter
	@JsonProperty("principal")
	private ZillowResult principal;

	@Getter
	@Setter
	private Comparables comparables;

	public Properties() {
	}
	public Properties(Comparables comparables) {
		this.comparables = comparables;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
