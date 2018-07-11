package com.cloud99.invest.domain.redis;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("AuthToken")
public class AuthToken implements Serializable {
	private static final long serialVersionUID = -7784848154484721038L;

	@Indexed
	private String token;

	private String userEmail;

	private DateTime expiryDate;
	private int expiryTimeInHours = 24;

	public AuthToken() {
	}


	@Transient
	private DateTime calculateExpiryDate(int expiryTimeInHours) {

		return DateTime.now().plusHours(expiryTimeInHours);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public DateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getExpiryTimeInHours() {
		return expiryTimeInHours;
	}

	public void setExpiryTimeInHours(int expiryTimeInHours) {
		this.expiryTimeInHours = expiryTimeInHours;
	}

}
