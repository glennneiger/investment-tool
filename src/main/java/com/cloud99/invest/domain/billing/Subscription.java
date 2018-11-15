package com.cloud99.invest.domain.billing;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.integration.payments.PaymentProviderInfo;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Representation of a recurring subscription with pricing amount and billing
 * frequency for a given user/account?
 */
@Data
@Document
public class Subscription implements MongoDocument {
	private static final long serialVersionUID = 4644145449186109577L;

	public enum BillingFrequency {
		MONTHLY, ANNUAL;
	}

	@Id
	private String id;

	@NotNull(message = "user.required")
	private String userId;

	// payment providers unique internal subscription id
	@NotNull(message = "")
	private String paymentProviderSubscriptionId;

	// payment provider for this subscription

	private PaymentProviderInfo paymentProvider;
	private LocalDate createDate = new LocalDate();
	private String description;
	private Money price;
	private BillingFrequency billingFrequency;

}
