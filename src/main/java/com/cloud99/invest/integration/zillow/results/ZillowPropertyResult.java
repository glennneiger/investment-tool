package com.cloud99.invest.integration.zillow.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowPropertyResult {
	private Message message;

	private ZillowResponse response;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
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
