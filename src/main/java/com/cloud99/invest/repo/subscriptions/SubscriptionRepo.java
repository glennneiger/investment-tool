package com.cloud99.invest.repo.subscriptions;

import com.cloud99.invest.domain.billing.Subscription;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends MongoRepository<Subscription, String>, SubscriptionRepoCustom {

	@Query("{'subscriptionBiller' : 'CLOUD99'}")
	Subscription findFreeSubscription();
	// @Query("{ 'activeDate' : { $gte : { $date : '0?' } } }")
	// List<Subscription> findActiveSubscriptions();
}
