package com.cloud99.invest.integration.data.zillow.domain;

/**
 * All Zillow root level result objects should implement this interface which
 * help provide generic processing on any API results from Zillow
 */
public interface ZillowApiResult {

	public ZillowMessage getMessage();
}
