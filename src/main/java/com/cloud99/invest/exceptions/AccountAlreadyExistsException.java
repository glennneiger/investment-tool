package com.cloud99.invest.exceptions;

public class AccountAlreadyExistsException extends ServiceException {
	private static final long serialVersionUID = -2968524255374395804L;
	private static final String ERROR_MESSAGE_CODE = "account.already.exist";

	public AccountAlreadyExistsException(String userId, String accountId) {
		super(ERROR_MESSAGE_CODE, "Account already exists for user: " + userId + ", acct: " + accountId);
	}

}
