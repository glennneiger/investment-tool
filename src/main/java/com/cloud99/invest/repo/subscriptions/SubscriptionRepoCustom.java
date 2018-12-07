package com.cloud99.invest.repo.subscriptions;

import com.cloud99.invest.domain.billing.Subscription;

import java.util.List;

public interface SubscriptionRepoCustom {

	List<Subscription> findByActiveSubscriptions();
}
