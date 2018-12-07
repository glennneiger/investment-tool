package com.cloud99.invest.integration.subscriptions;

import com.cloud99.invest.integration.subscriptions.stripe.StripeSubscriptionBillingProvider;

import lombok.Getter;

/**
 * This enum defines all of our 3rd party billing subscription providers and
 * their associated integration {@link SubscriptionBillingProvider} class.
 */
public enum SubscriptionBillerInfo {

	STRIPE(StripeSubscriptionBillingProvider.class),

	// this is our company, used for FREE subscriptions
	// TODO - NG - see about creating a no-op providerClass
	CLOUD99(null);

	@Getter
	private Class<? extends SubscriptionBillingProvider> providerClass;

	private SubscriptionBillerInfo(Class<? extends SubscriptionBillingProvider> providerClass) {
		this.providerClass = providerClass;
	}

	public static SubscriptionBillerInfo findFromName(String providerName) {
		for (SubscriptionBillerInfo info : values()) {
			if (info.name().equals(providerName)) {
				return info;
			}
		}
		return null;
	}
}
