package com.cloud99.invest.exceptions;

import com.cloud99.invest.integration.subscriptions.SubscriptionBillingProvider;

import lombok.Getter;

/**
 * Exception thrown from {@link SubscriptionBillingProvider} implementations.
 *
 */
public class PaymentProcessingException extends ServiceException {

	public enum PaymentProcessingExceptionCodes {
		PAYMENT_SOURCE_ERROR_CODE("payment.source.error");

		@Getter
		private String code;

		private PaymentProcessingExceptionCodes(String code) {
			this.code = code;
		}

	}

	public PaymentProcessingException(PaymentProcessingExceptionCodes code) {
		super(code.getCode());
	}
}
