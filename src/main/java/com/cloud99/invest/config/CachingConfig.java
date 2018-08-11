package com.cloud99.invest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableCaching
@ComponentScan(basePackages = "com.cloud99.invest")
@Order(20)
@Profile("production")
public class CachingConfig extends CachingConfigurerSupport {

	@Value("${redis.host}")
	private String hostName;

	@Value("${redis.port}")
	private int port;

	@Value("${redis.database}")
	private String database;

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

		// Defaults
		config.setHostName(hostName);
		config.setPort(port);

		return new JedisConnectionFactory(config);

	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory());

		return cacheManager;
	}

}
