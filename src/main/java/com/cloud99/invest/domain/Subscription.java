package com.cloud99.invest.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * The applications PAID membership payment subscriptions
 */
@Data
@Document
public class Subscription implements MongoDocument {
	private static final long serialVersionUID = -2596412939994768962L;


	public static enum SubscriptionType {
		MONTHLY, ANUALLY;
		// used by security evaluation to help refactorings no effect hard coded values
		// in permission evaluations
		public static final String SUBSCRIPTION_REF_NAME = "SubscriptionType";
	}

	@Id
	private String Id;
	private Double price;
	private String description;
	private SubscriptionType subscriptionType;

	// Billing subscription provider specific ID
	private String billingProviderId;

	private DateTime activeDate = DateTime.now();

}
