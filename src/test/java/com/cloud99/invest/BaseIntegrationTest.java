package com.cloud99.invest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloud99.invest.config.TestAppConfig;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.redis.AuthToken;
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
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Optional;

@ActiveProfiles("test")
// @EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo",
// "com.cloud99.invest.domain" })
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@Import(TestAppConfig.class)
@TestPropertySource("classpath:application-test.properties")
@OverrideAutoConfiguration(enabled = true)
@TestInstance(Lifecycle.PER_CLASS)
@WebAppConfiguration
// @ContextConfiguration(classes = TestBackendApplication.class)
public abstract class BaseIntegrationTest {

	public static final SimpleDateFormat UTC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS’Z");

	static {
		System.setProperty("spring.profiles.active", "test");
	}
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

		Mockito.when(authTokenRepo.findAuthTokenByUserId(user.getId())).thenReturn(dataCreator.buildAuthToken(user.getId()));
	}

	public String loginUser() throws Exception {

		MvcResult result = invokeLoginEndpoint(user.getEmail(), HttpStatus.OK);

		String authTokenJson = result.getResponse().getContentAsString();
		AuthToken authToken = objectMapper.readValue(authTokenJson, AuthToken.class);

		Mockito.when(authTokenRepo.findById(authToken.getToken())).thenReturn(Optional.of(authToken));
		return authToken.getToken();
	}

	protected MvcResult invokeLoginEndpoint(String email, HttpStatus expectedStatus) {
		MvcResult result = null;
		try {
			result = mvc.perform(post("/public/login")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.param("userEmail", email)
					.param("password", "password"))
					.andExpect(status().is(expectedStatus.value()))
					.andReturn();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
