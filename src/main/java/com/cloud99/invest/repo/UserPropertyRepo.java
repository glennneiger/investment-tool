package com.cloud99.invest.repo;

import com.cloud99.invest.domain.property.UserProperty;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPropertyRepo extends MongoRepository<UserProperty, String> {

	public UserProperty findByUserEmail(String userEmail);
}
