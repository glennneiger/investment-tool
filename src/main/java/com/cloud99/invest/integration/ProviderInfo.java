package com.cloud99.invest.integration;

import com.cloud99.invest.integration.zillow.ZillowServiceProvider;

import java.net.MalformedURLException;
import java.net.URL;

public enum ProviderInfo {

	ZILLOW(ZillowServiceProvider.class, "http://www.rentrange.com/");

	private URL logoLink;
	private Class<? extends ServiceProvider> providerImplementation;

	private ProviderInfo(Class<? extends ServiceProvider> providerImplementation, String logoLink) {
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

	public Class<? extends ServiceProvider> getProviderType() {
		return providerImplementation;
	}

}
