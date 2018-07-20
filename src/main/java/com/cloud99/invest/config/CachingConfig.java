package com.cloud99.invest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@ComponentScan(basePackages = "com.cloud99.invest")
@Order(20)
public class CachingConfig extends CachingConfigurerSupport {

	@Value("${redis.host}")
	private String hostName;

	@Value("${redis.port}")
	private Integer port;

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

	// @Bean
	// public CacheManager cacheManager(RedisTemplate redisTemplate) {
	// RedisCacheManager cacheManager = new RedisCacheManager(new
	// DefaultRedisCacheWriter(redisConnectionFactory()), redisTemplate);
	//
	// return cacheManager;
	// }

}
