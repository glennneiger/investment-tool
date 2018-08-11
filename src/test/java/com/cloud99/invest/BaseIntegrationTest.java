package com.cloud99.invest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.cloud99.invest.config.AppConfig;
import com.cloud99.invest.config.EmailConfig;
import com.cloud99.invest.config.MessageConfig;
import com.cloud99.invest.config.TestAppConfig;
import com.cloud99.invest.config.TestMongoConfig;
import com.cloud99.invest.config.WebSecurityConfig;
import com.cloud99.invest.security.GlobalMethodSecurityConfig;

import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo", "com.cloud99.invest.domain" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, GlobalMethodSecurityConfig.class, MessageConfig.class, WebSecurityConfig.class, EmailConfig.class, TestAppConfig.class, TestMongoConfig.class })
@WebAppConfiguration
public class BaseIntegrationTest {


	@Autowired
	protected WebApplicationContext context;

	protected DataCreator dataCreator = new DataCreator();
	protected MockMvc mvc;

	@BeforeAll
	public void beforeAll() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}



}
