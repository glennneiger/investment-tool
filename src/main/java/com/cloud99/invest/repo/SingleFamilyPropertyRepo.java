package com.cloud99.invest.repo;

import com.cloud99.invest.domain.property.SingleFamilyProperty;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleFamilyPropertyRepo extends MongoRepository<SingleFamilyProperty, String> {

}
