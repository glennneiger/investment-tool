package com.cloud99.invest.integration;

public class DataProviderException extends RuntimeException {

	private static final long serialVersionUID = 7135865486636932975L;

	private String messageCode;
	
	public DataProviderException(String messageCode, String msg) {
		super(msg);
		this.messageCode = messageCode;
	}

	public String getMessageCode() {
		return messageCode;
	}
}
