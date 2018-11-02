package com.cloud99.invest.integration.data.zillow.domain.property;

import com.cloud99.invest.integration.data.zillow.domain.ZillowApiResult;
import com.cloud99.invest.integration.data.zillow.domain.ZillowMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZillowPropertyDetailsResult implements ZillowApiResult {

	private ZillowMessage message;

	private Response response;

	private Request request;
}
