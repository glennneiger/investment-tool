package com.cloud99.invest.domain.billing;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.MembershipType;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import lombok.Data;

/**
 * This class represents an individual customers Paid membership and which
 * subscription plan thee customer has signed up for. This object is stored
 * within a {@link User} document if the customer has signed up for a Paid plan.
 * It only exists as an attribute of a {@link User}
 */
@Data
@Document
public class SubscriptionMembership implements Serializable {
	@Transient
	private static final long serialVersionUID = -6635707045340883727L;

	// actual subscription that this membership is associated with
	// @DBRef
	private String subscriptionId;

	private MembershipType membershipType;

	private DateTime createDate;

	// this will be true only if the customer's payment has been accepted and
	// processed successfully
	private boolean active;

}
