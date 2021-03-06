package com.cloud99.invest.integration.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataServiceProviderFactory {

	@Autowired
	private ApplicationContext applicationContext;
	
	public DataServiceProvider getServiceProvider(DataProviderInfo providerInfo) {

		DataServiceProvider provider = applicationContext.getBean(providerInfo.getProviderType());

		if (provider == null) {
			log.warn("No service provider implementation found: " + providerInfo);
		}
		return provider;
	}
}
