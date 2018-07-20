package com.cloud99.invest.exceptions;

public class EntityNotFoundException extends ServiceException {
	private static final long serialVersionUID = -1040433193790686267L;

	public EntityNotFoundException(String valueName, String messageValue) {
		super("entity.not.found", valueName, new String[] { messageValue });
	}

}
