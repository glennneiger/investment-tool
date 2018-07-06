package com.cloud99.invest.services.exceptions;

public class EntityNotFoundException extends ServiceException {
	private static final long serialVersionUID = -1040433193790686267L;

	private static final String notFoundCode = "account.not.found";

	public EntityNotFoundException(String details) {
		super(notFoundCode);
	}

}
