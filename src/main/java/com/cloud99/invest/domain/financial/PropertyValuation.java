package com.cloud99.invest.domain.financial;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.money.Money;

/**
 * Used for capturing detailed valuations for third-party integrations like
 * zillow
 */
public class PropertyValuation {

	private Money currentEstimate;
	private Money valueChange;
	private Money highValue;
	private Money lowValue;
	private Float percentileChange;

	private FinancialAssumptions assumptions;

	public FinancialAssumptions getAssumptions() {
		return assumptions;
	}

	public void setAssumptions(FinancialAssumptions assumptions) {
		this.assumptions = assumptions;
	}

	public Money getCurrentEstimate() {
		return currentEstimate;
	}

	public void setCurrentEstimate(Money currentEstimate) {
		this.currentEstimate = currentEstimate;
	}

	public Money getValueChange() {
		return valueChange;
	}

	public void setValueChange(Money valueChange) {
		this.valueChange = valueChange;
	}

	public Money getHighValue() {
		return highValue;
	}

	public void setHighValue(Money highValue) {
		this.highValue = highValue;
	}

	public Money getLowValue() {
		return lowValue;
	}

	public void setLowValue(Money lowValue) {
		this.lowValue = lowValue;
	}

	public Float getPercentileChange() {
		return percentileChange;
	}

	public void setPercentileChange(Float percentileChange) {
		this.percentileChange = percentileChange;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
