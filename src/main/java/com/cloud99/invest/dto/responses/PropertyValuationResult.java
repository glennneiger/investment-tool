package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.financial.PropertyValuation;
import com.cloud99.invest.integration.ProviderInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for returning property valuation data to a consumer
 */
public class PropertyValuationResult {

	@JsonIgnore
	@Setter
	@Getter
	private String providerId;

	@Setter
	@Getter
	private ProviderInfo providerInfo;

	private PropertyValuation propertyValuation;

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

	public void setDuration(Integer duration) {
		propertyValuation.setDuration(duration);
	}

	public Integer getDuration() {
		return propertyValuation.getDuration();
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

	public Double getPercentileChange() {
		return propertyValuation.getPercentileChange();
	}

	public void setPercentileChange(Double percentileChange) {
		propertyValuation.setPercentileChange(percentileChange);
	}

}
