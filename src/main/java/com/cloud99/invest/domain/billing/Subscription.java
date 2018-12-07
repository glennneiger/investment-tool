package com.cloud99.invest.domain.billing;

import com.cloud99.invest.domain.MongoDocument;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillerInfo;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * This class represents a subscription billing plan and it's details for PAID
 * memberships and their billing plan.
 * <p>
 * <b>Note:</b> this does not represent an individual membership, that info is
 * stored in a {@link SubscriptionMembership} within a {@link User} document.
 * </p>
 */
@Data
@Document
public class Subscription implements MongoDocument {
	private static final long serialVersionUID = -2596412939994768962L;


	public static enum Interval {
		MONTHLY, QUARTERLY, ANUALLY;
	}

	@Id
	private String id;

	// holds the 3rd party integration providers unique ID for a specific plan which
	// are pre-defined in the application.properties
	@NotNull
	private String providerSubscriptionPlanId;

	@NotNull
	private Double price;
	private String description;

	@NotNull
	private Interval billingInterval;

	@NotNull
	private DateTime activeDate;

	@NotNull
	private DateTime expireDate;

	@NotNull
	private SubscriptionBillerInfo subscriptionBiller;

	public boolean isActive() {
		DateTime now = DateTime.now();
		return (activeDate.isBefore(now) && (expireDate.isAfter(now)));
	}

}
