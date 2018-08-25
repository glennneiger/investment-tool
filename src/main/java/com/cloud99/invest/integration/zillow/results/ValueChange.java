package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueChange {

	@Getter
	@Setter
	private Double content;

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
