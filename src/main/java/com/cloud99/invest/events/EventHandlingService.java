package com.cloud99.invest.events;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.VerificationToken;
import com.cloud99.invest.services.EmailService;
import com.cloud99.invest.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * Centralized service to register all {@link EventListener} that handle
 * {@link ApplicationEvent}
 *
 */
@Service
public class EventHandlingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlingService.class);

	@Setter
	@Getter
	@Autowired
	private EmailService emailService;

	@Setter
	@Getter
	@Autowired
	private UserService userService;

	@EventListener
	public void registrationRequestListener(OnRegistrationRequestEvent event) {

		registrationRequestHandler(event);
	}

	/**
	 * Responds to registration request by creating a verification token and sending
	 * registration confirmation email
	 * 
	 * @param event
	 */
	@Async
	public void registrationRequestHandler(OnRegistrationRequestEvent event) {
		LOGGER.debug("registrationRequestHandler invoked for event: " + event);

		String userEmail = event.getUserEmail();
		User user = userService.findUserByEmailAndValidate(userEmail);

		VerificationToken token = userService.createVerificationToken(userEmail);

		// TODO - NG - find a way to get the full application URI (maybe the URL to our load balancer)
		String url = event.getAppUrl() + "/users/registrationConfirmation?token=" + token.getToken();
		emailService.sentUserRegistrationConfirmationEmail(user, url);
	}

}
