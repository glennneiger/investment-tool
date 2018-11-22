package com.cloud99.invest.integration.subscriptions.stripe;

import com.cloud99.invest.integration.subscriptions.SubscriptionBillingProvider;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:application-${spring.active.profiles}.properties")
@ConfigurationProperties(prefix = "stripe")
@Service
public class StripeSubscriptionBillingProvider implements SubscriptionBillingProvider {

	@NotEmpty
	private String publicKey;

	@NotEmpty
	private String privateKey;

	@NotEmpty
	private String baseUrl;

	private String billingPlanMonthly;
	private Double billingPlanMonthlyAmount;
	private String billingPlanAnnually;
	private Double billingPlanAnnuallyAmount;

	private void createProduct() {
		Stripe.apiKey = "sk_test_ShqIVHfLgMgtRAmxmzFHYKxF";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "My SaaS Platform");
		params.put("type", "service");

		try {
			Product stripeProduct = Product.create(params);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createSubscription(String userName, String subscriptionPlan, String providerToken) {
		// TODO Auto-generated method stub

	}

}
