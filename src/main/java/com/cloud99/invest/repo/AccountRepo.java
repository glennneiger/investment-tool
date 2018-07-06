package com.cloud99.invest.repo;

import com.cloud99.invest.domain.account.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends MongoRepository<Account, String> {

	Long deleteByName(String name);
}
