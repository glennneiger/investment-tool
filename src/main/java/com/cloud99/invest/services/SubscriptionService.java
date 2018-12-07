package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.dto.requests.CreateSubscriptionRequest;
import com.cloud99.invest.exceptions.ExistingPaidSubscriptionException;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillerFactory;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillerInfo;
import com.cloud99.invest.integration.subscriptions.SubscriptionBillingProvider;
import com.cloud99.invest.repo.subscriptions.SubscriptionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * This service deals with all PAID billing membership/subscription creation as
 * well as updating a users account with the features that relate to their
 * chosen membership/subscription type.
 */
@Slf4j
@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepo subscriptionRepo;

	@Autowired
	private SubscriptionBillerFactory subscriptionBillerFactory;

	@Autowired
	private SecurityService securityService;

	public List<Subscription> getActiveSubscriptions() {
		return subscriptionRepo.findByActiveSubscriptions();
	}

	/**
	 * @return the single FREE subscription
	 */
	public Subscription getFreeSubscription() {
		return subscriptionRepo.findFreeSubscription();
	}


	public void createSubscription(CreateSubscriptionRequest request) {
		log.trace("Starting to create subscription: " + request);

		User user = securityService.getCurrentSessionUser();

		checkExistingSubscription(user);

		// lookup the requested subscription plan
		Optional<Subscription> optional = subscriptionRepo.findById(request.getSubscriptionId());
		if (!optional.isPresent()) {
			throw new ServiceException("subscription.id.not.found");
		}
		
		if (!optional.get().isActive()) {
			throw new ServiceException("subscription.expired");
		}
		
		SubscriptionBillingProvider provider = subscriptionBillerFactory.getSubscriptionProvider(optional.get().getSubscriptionBiller());
		log.info("Starting to create subscription for provider: " + provider);

		provider.createSubscription(user, optional.get(), request.getProviderPaymentToken());

		// TODO - NG - find out how to associate paid account options based on
		// subscription type

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

		// need a way to associate a set of features to an individual subscriptions
	}

	private void checkExistingSubscription(User user) {
		// does the user already have a paid subscription?
		Optional<Subscription> existingSubOpt = subscriptionRepo.findById(user.getSubscriptionMembership().getSubscriptionId());
		if (user.getSubscriptionMembership().isActive() && existingSubOpt.isPresent()) {
			// is this a free subscription?
			if (!SubscriptionBillerInfo.CLOUD99.equals(existingSubOpt.get().getSubscriptionBiller())) {
				// nope, don't let the user override their existing subscription, they need to
				// update it
				throw new ExistingPaidSubscriptionException();
			}
		}
	}
}
