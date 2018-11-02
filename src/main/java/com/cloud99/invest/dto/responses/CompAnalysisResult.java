package com.cloud99.invest.dto.responses;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.money.Money;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class CompAnalysisResult implements Serializable {
	private static final long serialVersionUID = 155414190290554485L;

	@Getter
	@Setter
	private Money averageHighPrice;

	@Getter
	@Setter
	private Money averageLowPrice;

	@Getter
	@Setter
	private Money averageCurrentEstimate;

	@Getter
	@Setter
	private PropertyCompSearchResult searchResults;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
