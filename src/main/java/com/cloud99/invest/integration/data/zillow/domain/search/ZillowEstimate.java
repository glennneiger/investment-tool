package com.cloud99.invest.integration.data.zillow.domain.search;

import com.cloud99.invest.integration.data.zillow.deserializers.AmountJsonDeserializer;
import com.cloud99.invest.integration.data.zillow.deserializers.ValueChangeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowEstimate {

	@JsonDeserialize(using = AmountJsonDeserializer.class)
	@Getter
	@Setter
	private Amount amount;

	@Getter
	@Setter
	private Double percentile;

	@JsonDeserialize(using = ValueChangeDeserializer.class)
	@Getter
	@Setter
	private ValueChange durationChange;

	@Getter
	@Setter
	private String lastUpdated;

	@Getter
	@Setter
	private ValuationRange valuationRange;

	@JsonDeserialize(using = ValueChangeDeserializer.class)
	@Getter
	@Setter
	private ValueChange valueChange;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
