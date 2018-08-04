package com.cloud99.invest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class BaseToken {

	@Id
	@Indexed
	@Getter
	@Setter
	private String token;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private long expireTime;

	public BaseToken(String userId, int expiryTimeInMinutes) {
		this.userId = userId;
		this.token = UUID.randomUUID().toString();
		this.expireTime = calculationExpireTime(expiryTimeInMinutes);
	}

	@Transient
	protected long calculationExpireTime(int expiryTimeInMinutes) {
		return DateTime.now().plusMinutes(expiryTimeInMinutes).getMillis();
	}

	@JsonIgnore
	@Transient
	public DateTime getExpireDateTime() {
		return new DateTime(getExpireTime());
	}

}
