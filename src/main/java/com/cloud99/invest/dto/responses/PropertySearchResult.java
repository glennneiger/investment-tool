package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.ProviderInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

public class PropertySearchResult {

	@Setter
	@Getter
	private Property property;

	@Setter
	@Getter
	private ProviderInfo providerInfo;

	@Setter
	@Getter
	private String providerId;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
