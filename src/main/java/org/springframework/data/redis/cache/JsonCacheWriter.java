package org.springframework.data.redis.cache;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

public class JsonCacheWriter extends DefaultRedisCacheWriter {

	private ObjectMapper objectMapper;

	JsonCacheWriter(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
		super(connectionFactory);
		this.objectMapper = objectMapper;
	}

	@Override
	public void put(String name, byte[] key, byte[] value, Duration ttl) {
		// objectMapper.writevalu
		super.put(name, key, value, ttl);
	}

	@Override
	public byte[] get(String name, byte[] key) {
		// TODO Auto-generated method stub
		byte[] bytes = super.get(name, key);

		return bytes;

	}

	@Override
	public byte[] putIfAbsent(String name, byte[] key, byte[] value, Duration ttl) {
		// TODO Auto-generated method stub
		return super.putIfAbsent(name, key, value, ttl);
	}

	@Override
	public void remove(String name, byte[] key) {
		// TODO Auto-generated method stub
		super.remove(name, key);
	}

}
