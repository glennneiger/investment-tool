package com.cloud99.invest.events;

import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

// TODO - NG - see if we need this callback on expired auth tokens for anything
@Component
public class RedisExpireKeyEventListener implements ApplicationListener<RedisKeyExpiredEvent<?>> {

	@Override
	public void onApplicationEvent(RedisKeyExpiredEvent<?> event) {
		System.err.println("Exire event encountered: " + event);
	}

}
