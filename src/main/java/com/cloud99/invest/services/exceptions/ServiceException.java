package com.cloud99.invest.services.exceptions;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5399115508341659490L;

	// the key for messages in the messages_en_us.properties (or other locale)
	private String errorMessageCode;
	private String details;
	private ZonedDateTime createTime;
	private String txId;

	public ServiceException(String errorMessageCode) {
		this(errorMessageCode, null);
	}

	// TODO - NG - add stuff for validation errors
	public ServiceException(String errorMessageCode, String details) {
		super(errorMessageCode);
		this.details = details;
		txId = UUID.randomUUID().toString();
		createTime = ZonedDateTime.now();
	}

	public String getDetails() {
		return details;
	}

	public String getErrorMessage() {
		return errorMessageCode;
	}

	public ZonedDateTime getCreateTime() {
		return createTime;
	}

	public String getTransactionId() {
		return txId;
	}
}
