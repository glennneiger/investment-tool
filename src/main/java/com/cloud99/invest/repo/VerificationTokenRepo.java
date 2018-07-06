package com.cloud99.invest.repo;

import com.cloud99.invest.domain.VerificationToken;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends MongoRepository<VerificationToken, String> {

	VerificationToken findByToken(String token);

}
