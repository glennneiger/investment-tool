package com.cloud99.invest.services;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.AccountSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO - NG - this class needs to be enhanced to utilize internationalized
 * velocity templates and retrive the messages from the corresponding
 * message_.properties file
 */
@Slf4j
@Service("emailService")
@Primary
@PropertySource("application-${spring.active.profiles}.properties")
public class SendGridEmailService implements EmailService {

	private static final String CHARSET_UTF8 = "UTF-8";

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	/* (non-Javadoc)
	 * @see com.cloud99.invest.services.EmailServiceInterface#sentUserRegistrationConfirmationEmail(java.lang.String, java.lang.String, java.util.Locale)
	 */
	@Override
	public void sentUserRegistrationConfirmationEmail(User user, String appUrl) {

		AccountSettings settings = accountService.getUsersAccountSettings(user.getId());
		// TODO - NG - add configuration property to specify base URL and then tack on
		// the appUrl
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject(messages.getMessage("user.registration.request.subject", null, settings.getLocale()));
//		String body = messages.getMessage("user.registration.request.body", null, user.getLocale());
//		email.setText(body + "\n <a href=\"" + appUrl + " class=\"button\">Confirm Email Address</a>");

		Map<String, Object> model = new HashMap<>();
		model.put("user.name", user.getPersonName().getFirstName());
		model.put("user.email", user.getEmail());

		try {

			Template t = freemarkerConfig.getTemplate("welcomeEmail.ftl");

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			System.err.println(html);
			email.setText(html);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Sending user registration confirmation email: " + email);
		mailSender.send(email);

	}
}
