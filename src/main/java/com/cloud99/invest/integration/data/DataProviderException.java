package com.cloud99.invest.integration.data;

import lombok.Getter;
import lombok.Setter;

public class DataProviderException extends RuntimeException {

	private static final long serialVersionUID = 7135865486636932975L;

	@Getter
	private String messageCode;
	
	@Getter
	@Setter
	private String devMessage;

	public DataProviderException(String messageCode, String msg, String devMessage) {
		super(msg);
		this.messageCode = messageCode;
		this.devMessage = devMessage;
	}

}
