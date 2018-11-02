package com.cloud99.invest.integration.data.zillow.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LastSoldPrice {

	private String content;

	private String currency;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "ClassPojo [content = " + content + ", currency = " + currency + "]";
	}
}
