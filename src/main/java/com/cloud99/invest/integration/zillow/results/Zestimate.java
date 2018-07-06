package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zestimate {

	private Amount amount;

	private String percentile;

	private OneWeekChange oneWeekChange;

	private String lastUpdated;

	private ValuationRange valuationRange;

	private ValueChange valueChange;

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getPercentile() {
		return percentile;
	}

	public void setPercentile(String percentile) {
		this.percentile = percentile;
	}

	public OneWeekChange getOneWeekChange() {
		return oneWeekChange;
	}

	public void setOneWeekChange(OneWeekChange oneWeekChange) {
		this.oneWeekChange = oneWeekChange;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ValuationRange getValuationRange() {
		return valuationRange;
	}

	public void setValuationRange(ValuationRange valuationRange) {
		this.valuationRange = valuationRange;
	}

	public ValueChange getValueChange() {
		return valueChange;
	}

	public void setValueChange(ValueChange valueChange) {
		this.valueChange = valueChange;
	}

	@Override
	public String toString() {
		return "ClassPojo [amount = " + amount + ", percentile = " + percentile + ", oneWeekChange = " + oneWeekChange
				+ ", last-updated = " + lastUpdated + ", valuationRange = " + valuationRange + ", valueChange = "
				+ valueChange + "]";
	}
}
