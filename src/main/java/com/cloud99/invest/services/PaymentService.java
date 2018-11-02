package com.cloud99.invest.services;

import com.cloud99.invest.integration.payments.PaymentProvider;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {

	@Value("{stripe.api.key}")
	private String stripeApiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// @Autowired
	private PaymentProvider subscriptionProvider;

	@PostConstruct
	public void init() {
		// RequestOptions requestOptions =
		// RequestOptions.builder().setApiKey("sk_test_4eC39HqLyjWDarjtT1zdp7dc").build();

		// Charge.retrieve("ch_1DHD6T2eZvKYlo2Cf3fRkNLg", requestOptions)
	}

	// put in controller: request.getParameter("stripeToken")
	public void create(Payment payment, String paymentToken) {
		Map<String, Object> params = new HashMap<>();
		params.put("amount", 999);
		params.put("currency", "usd");
		params.put("description", "Example charge");
		params.put("source", paymentToken);
		try {
			Charge charge = Charge.create(params);
		} catch (StripeException e) {
			log.error(String.format(
					"Error occurred while trying to process payment: %s, code: %s, requestId: %s, statusCode: %d" 
					, e.getMessage(), e.getCode(), e.getRequestId(), e.getStatusCode()));
			e.printStackTrace();
		}

	}
}
