package com.cloud99.invest.config;

import com.cloud99.invest.events.EventHandlingService;
import com.cloud99.invest.repo.redis.AuthTokenRepo;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestAppConfig {

	protected AuthTokenRepo authTokenRepoMock;

	@Bean
	public EventHandlingService eventHandlingService() {
		return Mockito.spy(EventHandlingService.class);
	}
	
	@Bean
	public AuthTokenRepo authTokenRepo() {
		authTokenRepoMock = Mockito.mock(AuthTokenRepo.class);
		return authTokenRepoMock;
	}

	public AuthTokenRepo getAuthTokenRepo() {
		return authTokenRepoMock;
	}
}
