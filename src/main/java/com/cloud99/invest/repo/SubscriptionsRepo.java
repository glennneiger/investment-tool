package com.cloud99.invest.repo;

import com.cloud99.invest.domain.Subscription;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepo extends MongoRepository<Subscription, String> {

}
