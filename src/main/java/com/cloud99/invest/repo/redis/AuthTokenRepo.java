package com.cloud99.invest.repo.redis;

import com.cloud99.invest.domain.redis.AuthToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepo extends CrudRepository<AuthToken, String> {

	public AuthToken findAuthTokenByUserId(String userId);
}
