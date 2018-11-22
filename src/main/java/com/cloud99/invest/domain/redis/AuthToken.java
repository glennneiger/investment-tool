package com.cloud99.invest.domain.redis;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RedisHash("AuthToken")
public class AuthToken implements Serializable {
	private static final long serialVersionUID = -7784848154484721038L;

	@Id
	@Indexed
	@Getter
	@Setter
	private String token;

	@Getter
	@Setter
	private String userId;

	@TimeToLive
	@Getter
	@Setter
	private Integer timeToLiveSeconds;

	@Getter
	private final Date createTime = DateTime.now().toDate();

	@Getter
	private Date expireTime;

	public AuthToken(String userId, Integer timeToLiveSeconds) {
		this.userId = userId;
		this.timeToLiveSeconds = timeToLiveSeconds;
		this.token = UUID.randomUUID().toString();
		this.expireTime = DateTime.now().plusSeconds(timeToLiveSeconds).toDate();
	}

	public void setExpireTime(DateTime expire) {
		this.expireTime = expire.toDate();
	}

	@Transient
	public boolean isExpired() {
		return new DateTime(getExpireTime()).isBeforeNow();
	}
}
