package com.cloud99.invest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.cloud99.invest.config.EmailConfig;
import com.cloud99.invest.config.GlobalMethodSecurityConfig;
import com.cloud99.invest.config.MessageConfig;
import com.cloud99.invest.config.TestAppConfig;
import com.cloud99.invest.config.WebSecurityConfig;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.repo.AccountRepo;
import com.cloud99.invest.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;

@ActiveProfiles("test")
@EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo", "com.cloud99.invest.domain" })
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebSecurityConfig.class, GlobalMethodSecurityConfig.class, MessageConfig.class, EmailConfig.class, TestAppConfig.class })
@WebAppConfiguration
@TestPropertySource("classpath:application.properties")
@TestInstance(Lifecycle.PER_CLASS)
@Order(1)
public abstract class BaseIntegrationTest {

	public static final SimpleDateFormat UTC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS’Z");

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected WebApplicationContext context;

	protected DataCreator dataCreator = new DataCreator();
	protected MockMvc mvc;

	@Autowired
	protected UserRepo userRepo;

	@Autowired
	protected AccountRepo acctRepo;

	@Autowired
	protected com.cloud99.invest.repo.redis.AuthTokenRepo authTokenRepo;

	protected User user;
	protected Account account;

	@BeforeAll
	public void beforeAll() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		user = dataCreator.buildUser();
		user = userRepo.save(user);

		account = dataCreator.buildAccount(user.getId());
		account = acctRepo.save(account);

		Mockito.when(authTokenRepo.findAuthTokenByUserId(Mockito.anyString())).thenReturn(dataCreator.buildAuthToken(user.getId()));
	}

}
