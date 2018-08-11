package com.cloud99.invest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@Order(3)
public class EmailConfig {

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private int port;

	@Value("${mail.properties.mail.smtp.auth}")
	private Boolean auth;

	@Value("${mail.activation.password}")
	private String activationUserPassword;

	@Value("${mail.activation.username}")
	private String activationUser;

	@Value("${mail.properties.mail.smtp.starttls.enable}")
	private Boolean ttlsEnabled;

	@Value("${mail.debug}")
	private Boolean debug;

	@Bean
	public JavaMailSender getJavaMailSenders() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);

		mailSender.setUsername(activationUser);
		mailSender.setPassword(activationUserPassword);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", ttlsEnabled);
		props.put("mail.debug", debug);

		return mailSender;

	}

}
