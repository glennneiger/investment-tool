package com.cloud99.invest.exceptions;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5399115508341659490L;

	// the key for messages in the messages_en_us.properties (or other locale)
	@Getter
	@Setter
	private String errorMessageCode;

	@Getter
	@Setter
	private String devDetails;

	@Getter
	@Setter
	private ZonedDateTime createTime;

	@Getter
	@Setter
	private String txId;

	@Getter
	@Setter
	private String[] messageValues;

	public ServiceException(String errorMessageCode, String devDetails, String... messageValues) {
		super(errorMessageCode);
		this.devDetails = devDetails;
		txId = UUID.randomUUID().toString();
		createTime = ZonedDateTime.now();
		this.messageValues = messageValues;
	}

}
