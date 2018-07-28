package com.cloud99.invest.dto;

import com.cloud99.invest.domain.financial.PropertyValuation;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.ProviderInfo;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;

public class PropertyValuationResult {

	@Setter
	@Getter
	private String providerId;

	@Setter
	@Getter
	private ProviderInfo providerInfo;

	private PropertyValuation propertyValuation;

	@Setter
	@Getter
	private Property property;

	public PropertyValuationResult() {
		propertyValuation = new PropertyValuation();
	}

	public Money getCurrentEstimate() {
		return propertyValuation.getCurrentEstimate();
	}

	public void setCurrentEstimate(Money currentEstimate) {
		propertyValuation.setCurrentEstimate(currentEstimate);
	}

	public Money getValueChange() {
		return propertyValuation.getValueChange();
	}

	public void setValueChange(Money valueChange) {
		propertyValuation.setValueChange(valueChange);
	}

	public Money getHighValue() {
		return propertyValuation.getHighValue();
	}

	public void setHighValue(Money highValue) {
		propertyValuation.setHighValue(highValue);
	}

	public Money getLowValue() {
		return propertyValuation.getLowValue();
	}

	public void setLowValue(Money lowValue) {
		propertyValuation.setLowValue(lowValue);
	}

	public Float getPercentileChange() {
		return propertyValuation.getPercentileChange();
	}

	public void setPercentileChange(Float percentileChange) {
		propertyValuation.setPercentileChange(percentileChange);
	}

}
