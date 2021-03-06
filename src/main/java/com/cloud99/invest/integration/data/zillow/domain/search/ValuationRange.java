package com.cloud99.invest.integration.data.zillow.domain.search;

import com.cloud99.invest.integration.data.zillow.deserializers.HighPriceDeserializer;
import com.cloud99.invest.integration.data.zillow.deserializers.LowPriceDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValuationRange {

	@JsonDeserialize(using = HighPriceDeserializer.class)
	@Setter
	@Getter
	private High high;

	@JsonDeserialize(using = LowPriceDeserializer.class)
	@Setter
	@Getter
	private Low low;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
