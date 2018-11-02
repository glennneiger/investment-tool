package com.cloud99.invest.integration.data.zillow;

import com.cloud99.invest.integration.data.zillow.converterManagers.MessageConverterManager;
import com.cloud99.invest.integration.data.zillow.converterManagers.ZillowCompSearchResultsConverterManager;
import com.cloud99.invest.integration.data.zillow.converterManagers.ZillowSearchResultsMessageConverterManager;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Getter;

/**
 * This enum holds all of the information and details needed for processing of individual API's.
 * It lists the API's url, the {@link MessageConverterManager}, 
 */
public enum ZillowApiDetails {

	PROPERTY_SEARCH
	("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id={zws-id}&address={address}&citystatezip={citystatezip}&rentzestimate={rentzestimate}",
		ZillowSearchResultsMessageConverterManager.class),
	COMPS
	("http://www.zillow.com/webservice/GetDeepComps.htm?zws-id={zws-id}&zpid={zpid}&count={count}&rentzestimate={rentzestimate}",
			ZillowCompSearchResultsConverterManager.class)
	;

	@Getter
	private URL url;
	@Getter
	private Class<? extends MessageConverterManager<?, ?>> converterManager;
	
	private ZillowApiDetails(String urlStr, Class<? extends MessageConverterManager<?, ?>> converterManager) {
		try {
			this.url = new URL(urlStr);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid zillow API url: " + e.getMessage() + ", URL: " + urlStr, e);
		}
		this.converterManager = converterManager;
	}
	
}
