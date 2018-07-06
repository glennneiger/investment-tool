package com.cloud99.invest.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderFactory {
	private static final Logger log = LoggerFactory.getLogger(ServiceProviderFactory.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	public ServiceProvider getServiceProvider(ProviderInfo providerInfo) {

		ServiceProvider provider = applicationContext.getBean(providerInfo.getProviderType());

		if (provider == null) {
			log.warn("No service provider implementation found: " + providerInfo);
		}
		return provider;
	}
}
