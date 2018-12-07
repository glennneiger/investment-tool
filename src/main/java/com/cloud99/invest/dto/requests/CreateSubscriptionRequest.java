package com.cloud99.invest.dto.requests;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CreateSubscriptionRequest implements Serializable {

	// client side generated token for users payment details
	@NotEmpty(message = "subscription.provider.payment.token.required")
	private String providerPaymentToken;

	// our ID for subscriptions
	@NotEmpty(message = "subscription.id.required")
	private String subscriptionId;

}
