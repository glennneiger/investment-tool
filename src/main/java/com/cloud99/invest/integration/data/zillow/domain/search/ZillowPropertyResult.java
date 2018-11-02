package com.cloud99.invest.integration.data.zillow.domain.search;

import com.cloud99.invest.integration.data.zillow.domain.ZillowMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowPropertyResult {
	private ZillowMessage message;

	private ZillowResponse response;

	public ZillowMessage getMessage() {
		return message;
	}

	public void setMessage(ZillowMessage message) {
		this.message = message;
	}

	public ZillowResponse getResponse() {
		return response;
	}

	public void setResponse(ZillowResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "ClassPojo [message = " + message + ", response = " + response + "]";
	}
}
