package com.cloud99.invest.services.exceptions;

public class EntityNotFoundException extends ServiceException {
	private static final long serialVersionUID = -1040433193790686267L;

	public EntityNotFoundException(String detailMessageCode) {
		super(detailMessageCode);
	}

}
