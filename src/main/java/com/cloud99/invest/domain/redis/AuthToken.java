package com.cloud99.invest.domain.redis;

import com.cloud99.invest.domain.BaseToken;

import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RedisHash("AuthToken")
public class AuthToken extends BaseToken implements Serializable {
	private static final long serialVersionUID = -7784848154484721038L;

	public AuthToken(String userId, int expireTimeInMinutes) {
		super(userId, expireTimeInMinutes);
	}

	@TimeToLive
	@Override
	public long getExpireTime() {
		return super.getExpireTime();
	}

	@Transient
	public void updateExpireDateFromNow(int authTokenExpireTimeInMinutes) {
		this.setExpireTime(this.calculationExpireTime(authTokenExpireTimeInMinutes));

	}
}
