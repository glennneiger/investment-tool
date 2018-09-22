package com.cloud99.invest.config;

import com.cloud99.invest.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@EnableCaching
@Configuration
@ComponentScan(basePackages = { "com.cloud99.invest" })
@PropertySource("classpath:application.properties")
@EnableRedisRepositories(basePackages = "com.cloud99.invest.repo.redis", enableKeyspaceEvents = EnableKeyspaceEvents.ON_STARTUP)
@Order(20)
@Profile("production")
public class RedisConfig extends CachingConfigurerSupport {

	// TODO - NG - need to create password for redis and inject
	@Value("${spring.redis.host}")
	private String hostName;

	@Value("${spring.redis.port}")
	private Integer port;

	@Value("${spring.redis.database}")
	private Integer database;

	@Autowired
	private Util util;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@Bean
	public CacheManager cacheManager() {
		// RedisCacheManager cacheManager =
		// RedisCacheManager.create(jedisConnectionFactory());
		
		// DefaultConversionService con = new DefaultConversionService();
		// con.addConverter((Converter<?, ?>) new JsonRedisSerializer());
		
//		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory());
//		return builder.build();
		
		// RedisCacheConfiguration config =
		// cacheManager.getCacheConfigurations().get("").registerDefaultConverters(registry);
		// Map<String, RedisCacheConfiguration> configs =
		// cacheManager.getCacheConfigurations();
//		con.addConverter(converter);

		// return cacheManager;
		
		return  new ConcurrentMapCacheManager("users");
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {

		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(new JedisPoolConfig());
		RedisStandaloneConfiguration c = jedisConFactory.getStandaloneConfiguration();

		util.validateNotEmpty(hostName, "Redis host name was not injected from configuration");
		util.validateNotNull(port, "Redis port number was not injected from configuration");

		c.setHostName(hostName);
		c.setPort(port);

		return jedisConFactory;
	}


	@Bean
	@Primary
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();

		template.setConnectionFactory(jedisConnectionFactory());
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		return template;
	}

}
