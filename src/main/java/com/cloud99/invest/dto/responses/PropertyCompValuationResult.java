package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.property.Property;
import com.cloud99.invest.integration.data.DataProviderInfo;

import org.joda.money.Money;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PropertyCompValuationResult implements Serializable {
	private static final long serialVersionUID = -1532397622657032473L;

	// Holds the Zillow (or other data providers) score as one of the value (between
	// 0-100%). It indicates how closely the data provider rates a given comp
	// property
	@Setter
	@Getter
	private Double providerMatchingPercent;

	@Setter
	@Getter
	private PropertyValuationResult valuation;

	public PropertyCompValuationResult(PropertyValuationResult valuation) {
		this.valuation = valuation;
	}

	public PropertyCompValuationResult() {
		this.valuation = new PropertyValuationResult();
	}

	public String getProviderId() {
		return valuation.getProviderId();
	}

	public DataProviderInfo getProviderInfo() {
		return valuation.getProviderInfo();
	}

	public Money getCurrentEstimate() {
		return valuation.getCurrentEstimate();
	}

	public Property getProperty() {
		return valuation.getProperty();
	}

	public Money getValueChange() {
		return valuation.getValueChange();
	}

	public Integer getDurationDays() {
		return valuation.getDurationDays();
	}

	public Integer getDuration() {
		return valuation.getDuration();
	}

	public Money getHighValue() {
		return valuation.getHighValue();
	}

	public Money getLowValue() {
		return valuation.getLowValue();
	}

	public Double getPercentileChange() {
		return valuation.getPercentileChange();
	}

	public void setProviderId(String providerId) {
		valuation.setProviderId(providerId);
	}

	public void setProviderInfo(DataProviderInfo providerInfo) {
		valuation.setProviderInfo(providerInfo);
	}

	public void setProperty(Property property) {
		valuation.setProperty(property);
	}

}
