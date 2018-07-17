package com.cloud99.invest.events;

import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

import javax.sound.midi.SysexMessage;

@Component
public class RedisKeyExpiredEventListener implements ApplicationListener<RedisKeyExpiredEvent> {

	@Override
	public void onApplicationEvent(RedisKeyExpiredEvent event) {
		System.err.println("onApplicationEvent for: RedisKeyExpiredEvent invoked: " + event);
	}

}
