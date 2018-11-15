package com.cloud99.invest.integration.payments;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.billing.Product;
import com.cloud99.invest.domain.billing.Subscription;

/**
 * Interface used to integrate with third-party payment and billing systems
 */
public interface PaymentProvider {


	public User lookupCustomer(User user);

	public Subscription createSubscriptionPlan(Subscription plan);
}
