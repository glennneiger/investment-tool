package com.cloud99.invest.integration.subscriptions.stripe;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.exceptions.PaymentProcessingException;
import com.cloud99.invest.exceptions.PaymentProcessingException.PaymentProcessingExceptionCodes;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillingProvider;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:application-${spring.active.profiles}.properties")
@ConfigurationProperties(prefix = "stripe")
@Service
public class StripeSubscriptionBillingProvider implements SubscriptionBillingProvider {

	@NotEmpty
	@Value("${stripe.public.key}")
	private String publicKey;

	@NotEmpty
	@Value("${stripe.private.key}")
	private String privateKey;

	@NotEmpty
	@Value("${stripe.base.url}")
	private String baseUrl;

	@Override
	public void createSubscription(User user, Subscription subscription, String paymentToken) {
		Stripe.apiKey = privateKey;
		
		logCreateSubscription(user, subscription);

		// lookup subscription details

		// check for existing customer account (create if necessary and add payment source too)

		// create new customer account

		// submit subscription creation request

	}

	private void findCustomer(String customerId) {
		Map<String, Object> cust = new HashMap<>();

		try {
			Customer customer = Customer.retrieve(customerId);
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createNewCustomer(User user, String paymentSourceId, String invoiceDescr) throws StripeException {
		log.trace("Starting to create new customer: " + user.getEmail());

		Map<String, Object> item = new HashMap<>();
		item.put("usage", "reusable");
		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("object", "customer");
		customerParams.put("email", user.getEmail());
		customerParams.put("source", paymentSourceId);
		customerParams.put("description", invoiceDescr);

		Customer customer = Customer.create(customerParams);
		return null;
	}

	private void createPaymentSource(User user, String invoiceDescr) {
		log.trace("Creating payment source for user: " + user.getEmail());

		try {
			Map<String, Object> sourceParams = new HashMap<>();
			sourceParams.put("type", "ach_credit_transfer");
			sourceParams.put("currency", "usd");
			sourceParams.put("statement_descriptor", invoiceDescr);
			sourceParams.put("usage", "reusable");

			Map<String, Object> ownerParams = new HashMap<>();
			ownerParams.put("email", user.getEmail());
			sourceParams.put("owner", ownerParams);

			Source.create(sourceParams);
		} catch (StripeException e) {
			log.error(String.format("Error occurred while trying to process the customers: %s payment request: %s", user.getEmail(), e.getMessage()), e);
			throw new PaymentProcessingException(PaymentProcessingExceptionCodes.PAYMENT_SOURCE_ERROR_CODE);
		}
	}

	private void sendSubscriptionRequest(Subscription subscription, String stripCustomerId, String paymentToken) {

		Map<String, Object> item = new HashMap<>();
		item.put("amount", subscription.getPrice());
		item.put("currency", "usd");
		item.put("description", buildSubscriptionDescr(subscription.getBillingInterval().name()));
		item.put("source", paymentToken);
		item.put("plan", subscription.getProviderSubscriptionPlanId());
		
		Map<String, Object> items = new HashMap<>();
		items.put("0", item);
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer", stripCustomerId);
		params.put("items", items);
		
		try {
			com.stripe.model.Subscription stripSub = com.stripe.model.Subscription.create(params); 

		} catch (StripeException e) {
			log.error(String.format("Error occurred while trying to process payment: %s, code: %s, requestId: %s, statusCode: %d", e.getMessage(), e.getCode(), e.getRequestId(), e.getStatusCode()));
			e.printStackTrace();
		}
	}

	private void logCreateSubscription(User user, Subscription subscription) {
		log.trace("Starting to create new user subscription for user: {}, subscriptionId: {}, providerPlanId: {}", user.getId(), subscription.getId(), subscription.getProviderSubscriptionPlanId());
	}

	/*
	 * Build a subscription create description If this application is ever truly
	 * internationalized then this description should be extracted from a
	 * message.properties file and use string substitution for values
	 */
	private String buildSubscriptionDescr(String billingInterval) {
		return String.format("%s Investment Analyzr Application Subscription", billingInterval);
	}

}
