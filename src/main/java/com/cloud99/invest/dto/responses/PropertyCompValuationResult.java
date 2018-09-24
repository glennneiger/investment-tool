package com.cloud99.invest.dto.responses;

import lombok.Getter;
import lombok.Setter;

public class PropertyCompValuationResult extends PropertyValuationResult {
	private static final long serialVersionUID = -1532397622657032473L;

	// Holds the Zillow (or other data providers) score as one of the value (between
	// 0-100%). It indicates how closely the data provider rates a given comp
	// property
	@Setter
	@Getter
	private Double providerMatchingPercent;

}
