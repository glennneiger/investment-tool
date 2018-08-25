package com.cloud99.invest.dto.responses;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.Money;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class FlipAnalysisResults {

	@Getter
	@Setter
	private Money maxAllowableOfferPrice;

	@Getter
	@Setter
	private Double returnOnInvestment;

	// value = number of days -> profile amount
	@Getter
	@Setter
	private Map<Integer, Money> hypotheticalForecast;
	
	@Getter
	@Setter
	private Double rateOfReturn;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	// Flip Hypothetical Profit If Held For...
	// 45, 90, 270 days
}
