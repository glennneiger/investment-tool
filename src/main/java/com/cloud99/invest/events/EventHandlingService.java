package com.cloud99.invest.events;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.repo.GenericRepo;
import com.cloud99.invest.services.AccountService;
import com.cloud99.invest.services.EmailService;
import com.cloud99.invest.services.SecurityService;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Centralized service to register all {@link EventListener} that handle
 * {@link ApplicationEvent}
 *
 */
@Slf4j
@Service
public class EventHandlingService {

	@Setter
	@Getter
	@Autowired
	private EmailService emailService;

	@Setter
	@Getter
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private GenericRepo genericRepo;

	@Autowired
	private AccountService accountService;

	@EventListener
	public void accountCreatedEventListener(AccountCreatedEvent event) {

		log.debug(AccountCreatedEvent.class.getSimpleName() + " event listener invoked for event: " + event);

		Account acct = event.getAccount();

		acct.setClosingCostsList(genericRepo.getCollectionList(ItemizedCost.class, GenericRepo.CLOSING_COSTS_COLLECTION_NAME));
		acct.setExpencesList(genericRepo.getCollectionList(ItemizedCost.class, GenericRepo.EXPENCES_COSTS_COLLECTION_NAME));
		acct.setHoldingCostList(genericRepo.getCollectionList(ItemizedCost.class, GenericRepo.HOLDING_COSTS_COLLECTION_NAME));

		accountService.save(acct);
		log.debug("Finished loading account itemized costs ref data");
	}

	/**
	 * Responds to registration request by creating a verification token and sending
	 * registration confirmation email
	 * 
	 * @param event
	 *            that was generated from a new account registration
	 */
	@EventListener
	public void registrationRequestListener(OnRegistrationRequestEvent event) {

		registrationRequestHandler(event);
	}

	private void registrationRequestHandler(OnRegistrationRequestEvent event) {
		log.debug("registrationRequestHandler invoked for event: " + event);

		String userEmail = event.getUserEmail();
		User user = userService.findUserByEmailAndValidate(userEmail);

		VerificationToken token = securityService.createVerificationToken(userEmail);

		// TODO - NG - find a way to get the full application URI (maybe the URL to our load balancer)
		String url = event.getAppUrl() + "/users/registrationConfirmation?token=" + token.getToken();
		emailService.sentUserRegistrationConfirmationEmail(user, url);
	}

}
