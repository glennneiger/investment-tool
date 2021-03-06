package com.cloud99.invest.integration.data;

import com.cloud99.invest.integration.data.zillow.serviceProviders.ZillowServiceProvider;

import java.net.MalformedURLException;
import java.net.URL;

public enum DataProviderInfo {

	// TODO - NG - change the URL logo link to be relative to the controller that
	// will host out the file or a link to a CDN
	ZILLOW(ZillowServiceProvider.class, "http://www.rentrange.com/");

	private URL logoLink;
	private Class<? extends DataServiceProvider> providerImplementation;

	private DataProviderInfo(Class<? extends DataServiceProvider> providerImplementation, String logoLink) {
		this.providerImplementation = providerImplementation;

		try {
			this.logoLink = new URL(logoLink);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public URL getLogoURL() {
		return logoLink;
	}

	public Class<? extends DataServiceProvider> getProviderType() {
		return providerImplementation;
	}

}
