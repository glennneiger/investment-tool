package com.cloud99.invest.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

public class VerificationToken implements MongoDocument {
	private static final long serialVersionUID = 7857956068873809073L;

	@Id
	private String id;

	@Indexed
	private String token;

	private String userEmail;

	private DateTime expiryDate;
	private String accountId;
	private int expiryTimeInHours = 24;

	public VerificationToken() {
	}

	public VerificationToken(int expiryTimeInHours, String userEmail, String accountId) {
		this.expiryTimeInHours = expiryTimeInHours;
		expiryDate = calculateExpiryDate(expiryTimeInHours);
		this.token = UUID.randomUUID().toString();
		this.userEmail = userEmail;
		this.accountId = accountId;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Transient
	public DateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getExpiryTimeInHours() {
		return expiryTimeInHours;
	}

	public void setExpiryTimeInHours(int expiryTimeInHours) {
		this.expiryTimeInHours = expiryTimeInHours;
	}

	@Override
	public String toString() {
		return toJsonString();
	}
}
