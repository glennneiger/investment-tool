package com.cloud99.invest.dto.requests;

import lombok.Data;

@Data
public class PaymentRequest {

	private String stripeToken;
	private String stripeTokenType;
	private String stripeEmail;

}
