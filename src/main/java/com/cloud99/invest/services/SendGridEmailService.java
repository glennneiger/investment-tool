package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class SendGridEmailService implements EmailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SendGridEmailService.class);

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	/* (non-Javadoc)
	 * @see com.cloud99.invest.services.EmailServiceInterface#sentUserRegistrationConfirmationEmail(java.lang.String, java.lang.String, java.util.Locale)
	 */
	@Override
	public void sentUserRegistrationConfirmationEmail(User user, String appUrl) {

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject(messages.getMessage("user.registration.request.subject", null, user.getLocale()));
		String body = messages.getMessage("user.registration.request.body", null, user.getLocale());
		email.setText(body + "\n <a href=\"" + appUrl + " class=\"button\">Confirm Email Address</a>");

		LOGGER.debug("Sending user registration confirmation email: " + email);
		mailSender.send(email);

	}
}
