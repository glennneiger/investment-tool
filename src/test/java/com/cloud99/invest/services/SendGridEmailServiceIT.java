package com.cloud99.invest.services;

import com.cloud99.invest.DataCreator;
import com.cloud99.invest.config.EmailConfig;
import com.cloud99.invest.config.GlobalMethodSecurityConfig;
import com.cloud99.invest.config.MessageConfig;
import com.cloud99.invest.config.MongoConfig;
import com.cloud99.invest.config.TestAppConfig;
import com.cloud99.invest.config.WebSecurityConfig;
import com.cloud99.invest.domain.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("test")
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebSecurityConfig.class, GlobalMethodSecurityConfig.class, MessageConfig.class, WebSecurityConfig.class, EmailConfig.class, TestAppConfig.class })
@ComponentScan(
	    basePackages="com.cloud99.invest",
	    excludeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE,
	                    value = {
	                            MongoConfig.class,
						// TestMongoConfig.class
	            })
	    })
public class SendGridEmailServiceIT {

	@Autowired
	private SendGridEmailService emailService;

	protected DataCreator dataCreator = new DataCreator();
	@Test
	public void testRegistration() {
		User user = dataCreator.buildUser();
		emailService.sentUserRegistrationConfirmationEmail(user, "http://cloud99.com");
	}
}
