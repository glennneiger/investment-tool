package com.cloud99.invest.domain.billing;

import com.cloud99.invest.integration.subscriptions.SubscriptionBillerInfo;

import org.joda.money.CurrencyUnit;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import lombok.Data;

/**
 * Invoice that gets generated for each billing period in a Subscription
 * 
 * Stripe Webhook: invoice.created Stripe API:
 * https://stripe.com/docs/api/invoices?lang=java
 */
@Data
public class Invoice implements Serializable {
	private static final long serialVersionUID = -6084967388888015514L;

	public static enum Status {
		PENDING, PAID, FAILED;
	}
	@Id
	private String id;

	@NotNull
	private SubscriptionBillerInfo billerInfo;

	@NotNull
	private String providerInvoiceId;

	@NotNull
	private DateTime createDate;
	private DateTime dueDate;
	private String description;

	@NotNull
	private Status status = Status.PENDING;

	private DateTime statusChangeDate;

	@NotNull
	private CurrencyUnit currency = CurrencyUnit.USD;

	@DBRef
	private String userId;

	@NotNull
	private Double amountDue;

}
