package com.cloud99.invest.repo.redis;

import com.cloud99.invest.domain.redis.AuthToken;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface AuthTokenRepo extends CrudRepository<AuthToken, String> {

	public AuthToken findAuthTokenByUserId(String userId);
}
