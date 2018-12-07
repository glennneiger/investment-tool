package com.cloud99.invest.dto.responses;

import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.domain.billing.Subscription.Interval;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveSubscriptionResponse implements Serializable {
	private static final long serialVersionUID = -4217312104550736639L;

	private String id;
	private Interval billingInterval;
	private Double price;
	private String description;
	
	public static ActiveSubscriptionResponse buildFromSubscription(Subscription subscription) {
		return new ActiveSubscriptionResponse(subscription.getId(), subscription.getBillingInterval(), subscription.getPrice(), subscription.getDescription());
	}
}
