package com.cloud99.invest.integration.data.zillow.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueChange {

	@Getter
	@Setter
	private BigDecimal content = new BigDecimal(0).setScale(2);

	@Getter
	@Setter
	private Integer duration;

	@Getter
	@Setter
	private String currency;

	@Override
	public String toString() {
		return "ClassPojo [content = " + content + ", duration = " + duration + ", currency = " + currency + "]";
	}
}
