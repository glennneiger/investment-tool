package com.cloud99.invest.integration.data.zillow.domain.comps;

import com.cloud99.invest.integration.data.zillow.domain.search.ZillowSearchResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ZillowComp extends ZillowSearchResult {

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
