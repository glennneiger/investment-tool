package com.cloud99.invest.repo;

import com.cloud99.invest.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

	Long deleteUserByEmail(String email);

	@Query(value = "{ 'email' : ?0 }")
	User findByEmail(String email);

	Long deleteByEmail(String email);
}
