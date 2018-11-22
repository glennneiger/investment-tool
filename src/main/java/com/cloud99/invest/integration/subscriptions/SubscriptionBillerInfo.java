package com.cloud99.invest.integration.subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public enum SubscriptionBillerInfo {

	STRIPE;

	private Class<? extends SubscriptionBillingProvider> providerClass;


}
