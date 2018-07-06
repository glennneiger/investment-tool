package com.cloud99.invest.dto;

import com.cloud99.invest.domain.financial.PropertyValuation;
import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.ProviderInfo;

import org.joda.money.Money;

public class PropertyValuationResult {

	private String providerId;

	private ProviderInfo providerInfo;

	private PropertyValuation propertyValuation;
	private Property property;

	public PropertyValuationResult() {
		propertyValuation = new PropertyValuation();
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
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

	public boolean equals(Object obj) {
		return propertyValuation.equals(obj);
	}

	public ProviderInfo getProviderInfo() {
		return providerInfo;
	}

	public void setProviderInfo(ProviderInfo providerInfo) {
		this.providerInfo = providerInfo;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
