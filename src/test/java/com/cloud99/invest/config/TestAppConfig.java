package com.cloud99.invest.config;

import com.cloud99.invest.events.EventHandlingService;
import com.cloud99.invest.repo.redis.AuthTokenRepo;

import org.apache.logging.log4j.core.config.Order;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile({ "test", "test-data" })
@AutoConfigureWebMvc
@EnableWebMvc
//@ComponentScan(basePackages = { "com.cloud99.invest.services", "com.cloud99.invest.util", "com.cloud99.invest.controller" })
@ComponentScan(basePackages = { "com.cloud99.invest" })
@Order(1)
public class TestAppConfig {

	protected AuthTokenRepo authTokenRepoMock;

//	@Bean
//	public MongoTemplate mongoTemplate() {
//		MongoTemplate t = Mockito.mock(MongoTemplate.class);
//		MongoConverter converter = Mockito.mock(MongoConverter.class);
//		Mockito.when(t.getConverter()).thenReturn(converter);
//		Mockito.when(converter.getMappingContext()).thenReturn(new MongoMappingContext());
//		return t;
//	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("application-${spring.active.profiles}.properties") };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(false);
		return pspc;
	}

	@Bean
	public EventHandlingService eventHandlingService() {
		return Mockito.spy(EventHandlingService.class);
	}

	@Bean
	public AuthTokenRepo authTokenRepo() {
		if (authTokenRepoMock == null) {
			authTokenRepoMock = Mockito.mock(AuthTokenRepo.class);
		}
		return authTokenRepoMock;
	}

}
