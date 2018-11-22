package com.cloud99.invest.services;

import com.cloud99.invest.domain.Subscription;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillerInfo;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillingProvider;
import com.cloud99.invest.repo.SubscriptionsRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubscriptionService {

	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SubscriptionBillingProvider billingProvider;

	@Autowired
	private SubscriptionsRepo subscriptionRepo;

	public List<Subscription> getAllActiveSubscriptions() {
		return subscriptionRepo.findAll();
	}

	// put in controller: request.getParameter("stripeToken")
	public void createSubscription(String userName, String paymentToken, SubscriptionBillerInfo billerInfo) {

		// make sure the user doesn't already have a subscription

		// Update the account setting for paid user values
		// if (MembershipType.PAID.equals(request.getMembershipType())) {
		// numOfProperties = -1;
		// acctOptions.setAllowedToStoreDocuments(true);
		// acctOptions.setNumberOfPropertiesUserCanStore(-1);
		// } else {
		// // FREE user settings
		// acctOptions.setAllowedToStoreDocuments(false);
		// acctOptions.setNumberOfPropertiesUserCanStore(numOfProperties);
		// }

		// does customer already have a billing provider account?
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
