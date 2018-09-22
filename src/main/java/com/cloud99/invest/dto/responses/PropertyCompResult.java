package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.property.Property;

import lombok.Getter;
import lombok.Setter;

public class PropertyCompResult extends PropertyValuationResult {

	@Setter
	@Getter
	private Property subjectProperty;

	// Holds the Zillow score as one of the value (between 0-100%)
	@Setter
	@Getter
	private Double providerMatchingPercent;

}
