package com.cloud99.invest.domain.redis;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.UUID;

// TODO - NG - make this an abstract base class and update the VerificationToken to extends the abstract
@RedisHash("AuthToken")
public class AuthToken implements Serializable {
	private static final long serialVersionUID = -7784848154484721038L;

	@Id
	@Indexed
	private String token;

	private String userId;

	private Long expiryDate;

	// TODO - NG - move this to a config property
	private int expiryTimeInHours = 24;

	@Transient
	private DateTime expiryDateTime;

	@SuppressWarnings("boxing")
	public AuthToken(String userId) {
		this.userId = userId;
		token = UUID.randomUUID().toString();
		expiryDate = setExpiryDate(getExpiryTimeInHours()).getMillis();
	}

	public DateTime getExpiryDateTime() {
		return this.expiryDateTime;
	}

	@TimeToLive
	@Transient
	private DateTime setExpiryDate(int expiryTimeInHours) {

		this.expiryDateTime = DateTime.now().plusHours(expiryTimeInHours);
		return expiryDateTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getExpiryTimeInHours() {
		return expiryTimeInHours;
	}

	public void setExpiryTimeInHours(int expiryTimeInHours) {
		this.expiryTimeInHours = expiryTimeInHours;
	}

}
