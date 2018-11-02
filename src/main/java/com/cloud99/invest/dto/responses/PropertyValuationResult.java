package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.financial.PropertyValuation;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.data.ProviderInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for returning property valuation data to a consumer
 */
public class PropertyValuationResult extends PropertyValuation {
	private static final long serialVersionUID = -4963686870805465531L;

	@JsonIgnore
	@Setter
	@Getter
	private String providerId;

	@Setter
	@Getter
	private ProviderInfo providerInfo;

	@Setter
	@Getter
	private Property property;

}
