package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowEstimate {

	@Getter
	@Setter
	private Amount amount;

	@Getter
	@Setter
	private Float percentile;

	@Getter
	@Setter
	private ValueChange durationChange;

	@Getter
	@Setter
	private String lastUpdated;

	@Getter
	@Setter
	private ValuationRange valuationRange;

	@Getter
	@Setter
	private ValueChange valueChange;

	@Override
	public String toString() {
		return "ClassPojo [amount = " + amount + ", percentile = " + percentile + ", oneWeekChange = " + durationChange
				+ ", last-updated = " + lastUpdated + ", valuationRange = " + valuationRange + ", valueChange = "
				+ valueChange + "]";
	}
}
