package com.cloud99.invest.integration.subscriptions;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.dto.requests.CreateSubscriptionRequest;

/**
 * Interface that defines the needed behavior needed of our 3rd-party payment
 * and billing systems integrations.
 */
public interface SubscriptionBillingProvider {

	void createSubscription(User user, Subscription subscription, String paymentToken);

}
