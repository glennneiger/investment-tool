package com.cloud99.invest.integration.subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SubscriptionBillerFactory {

	@Autowired
	private ApplicationContext applicationContext;

	public SubscriptionBillingProvider getSubscriptionProvider(SubscriptionBillerInfo providerInfo) {

		SubscriptionBillingProvider provider = applicationContext.getBean(providerInfo.getProviderClass());

		if (provider == null) {
			log.warn("No billing subscription provider implementation found for type: " + providerInfo);
		}
		return provider;
	}
}
