package com.cloud99.invest.exceptions;

public class SecurityException extends RuntimeException {
	private static final long serialVersionUID = 581358951984345511L;

	public SecurityException(String message, Throwable t) {
		super(message, t);
	}
}
