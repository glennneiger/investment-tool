package com.cloud99.invest.repo;

import com.cloud99.invest.domain.property.Property;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepo extends MongoRepository<Property, String> {

	Property findByName(String name);
}
