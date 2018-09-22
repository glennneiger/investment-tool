package com.cloud99.invest.integration.zillow.results.comps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowResponse {

	@Getter
	@Setter
	private Properties properties;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
