package com.cloud99.invest.events;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.services.EmailServiceInterface;
import com.cloud99.invest.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Centralized service to register all {@link EventListener} that handle
 * {@link ApplicationEvent}
 *
 */
@Service
public class EventHandlingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlingService.class);

	@Autowired
	private EmailServiceInterface emailService;

	@Autowired
	private UserService userService;

	@EventListener
	public void registrationRequestListener(OnRegistrationRequestEvent event) {

		registrationRequestHandler(event);
	}

	@Async
	public void registrationRequestHandler(OnRegistrationRequestEvent event) {
		LOGGER.debug("registrationRequestHandler invoked for event: " + event);

		String userEmail = event.getUserEmail();
		User user = userService.findUserByEmail(userEmail);
		Account acct = event.getAccount();
		VerificationToken token = userService.createVerificationToken(userEmail, acct.getId());

		String url = event.getAppUrl() + "/users/registrationConfirmation?token=" + token.getToken();
		emailService.sentUserRegistrationConfirmationEmail(user, url);
	}

}
