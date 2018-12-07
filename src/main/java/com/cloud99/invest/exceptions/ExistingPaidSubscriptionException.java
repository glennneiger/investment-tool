package com.cloud99.invest.exceptions;

/**
 * Used to indicate if a customer is trying to create new subscription when they
 * already have an active paid membership.
 */
public class ExistingPaidSubscriptionException extends ServiceException {

	private static final String ERROR_CODE = "paid.subscription.exists";

	public ExistingPaidSubscriptionException() {
		super(ERROR_CODE);
	}

}
