package com.cloud99.invest.integration.zillow.results.comps;

import com.cloud99.invest.integration.zillow.results.ZillowResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Comp extends ZillowResult {

	@Setter
	@Getter
	private Double score;

	@Override
	public String getUseCode() {
		// This is hardcoded because this element doesn't exist in zillows comp response
		// api
		return "SingleFamily";
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
