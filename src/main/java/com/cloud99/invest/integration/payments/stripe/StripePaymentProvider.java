package com.cloud99.invest.integration.payments.stripe;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.integration.payments.PaymentProvider;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service
public class StripePaymentProvider implements PaymentProvider {

	@Value("${stripe.public.key}")
	private String publicKey;

	@Value("${stripe.private.key}")
	private String privateKey;

	@Value("${stripe.base.url}")
	private String baseUrl;

	private void createProduct() {
		Stripe.apiKey = "sk_test_ShqIVHfLgMgtRAmxmzFHYKxF";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "My SaaS Platform");
		params.put("type", "service");

		try {
			Product stripProduct = Product.create(params);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void processPayment() {
		// create new customer
		// associate the customer to an existing plan -
		//
	}

	@Override
	public Subscription createSubscriptionPlan(Subscription plan) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User lookupCustomer(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
