package com.cloud99.invest.integration.subscriptions;

import com.cloud99.invest.domain.User;

/**
 * Interface that defines the needed behavior of our third-party payment and
 * billing systems
 */
public interface SubscriptionBillingProvider {

	public void createSubscription(String userId, String subscriptionPlan, String providerToken);
}
