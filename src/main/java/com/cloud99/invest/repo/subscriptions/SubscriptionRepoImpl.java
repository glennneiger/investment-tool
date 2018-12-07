package com.cloud99.invest.repo.subscriptions;

import com.cloud99.invest.domain.billing.Subscription;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class SubscriptionRepoImpl implements SubscriptionRepoCustom {

	@Autowired
	private MongoTemplate mongo;

	@Override
	public List<Subscription> findByActiveSubscriptions() {
		Query query = new Query();
		DateTime now = DateTime.now();
		query.addCriteria(Criteria.where("expireDate").gt(now).andOperator(Criteria.where("activeDate").lt(now)));
		return mongo.find(query, Subscription.class);
	}
}
