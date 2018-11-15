package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.data.DataProviderInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;

public class PropertySearchResult {

	@Setter
	@Getter
	private Property property;

	@Setter
	@Getter
	private DataProviderInfo providerInfo;

	@Setter
	@Getter
	private String providerId;

	@Getter
	@Setter
	private URL propertyPicture;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
