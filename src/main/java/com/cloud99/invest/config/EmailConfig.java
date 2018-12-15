package com.cloud99.invest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.properties")
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

//	@Bean
//	public VelocityEngine velocityEngine() {
//		VelocityEngine e = new VelocityEngine("src/main/resources/application.properties");
//
//		return e;
//	}
	
	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("templates/");
        return bean;
    }

}
