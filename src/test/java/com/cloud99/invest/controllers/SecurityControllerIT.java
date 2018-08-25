package com.cloud99.invest.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.controller.SecurityController;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.services.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

public class SecurityControllerIT extends BaseIntegrationTest {

	@Autowired
	private SecurityController securityController;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@BeforeAll
	public void beforeClass() {
		super.beforeAll();
	}

	@Test
	public void loginTest() throws Exception {

		User user = super.dataCreator.buildUser();

		try {

			userService.createUser(user, UserRole.CUSTOMER);
			user.setEnabled(true);
			userRepo.save(user);

			MvcResult result = invokeLoginEndpoint(user, HttpStatus.OK);
					
			String authTokenJson = result.getResponse().getContentAsString();
			AuthToken authToken = objectMapper.readValue(authTokenJson, AuthToken.class);
			
			Assertions.assertAll("Auth token is missing required attribute values", 
			() -> assertNotNull(authToken),
			() -> assertNotNull(authToken.getExpireDateTime()),
			() -> assertNotNull(authToken.getToken()),
			() -> assertEquals(user.getId(), authToken.getUserId()));

			Mockito.when(authTokenRepo.findById(authToken.getToken())).thenReturn(Optional.of(authToken));

			// now try to access a secured endpoint
			assertEquals(HttpStatus.OK.value(), invokeSecuredEnpoint(user, authToken).getResponse().getStatus());
			
		} finally {
			userRepo.delete(user);
		}
	}

	@Test
	public void logout() throws Exception {

		User user = null;
		try {
			user = super.dataCreator.buildUser();
			userService.createUser(user, UserRole.CUSTOMER);
			user.setEnabled(true);
			userRepo.save(user);

			// login
			MvcResult result = invokeLoginEndpoint(user, HttpStatus.OK);

			String authTokenJson = result.getResponse().getContentAsString();
			AuthToken authToken = objectMapper.readValue(authTokenJson, AuthToken.class);

			Mockito.when(authTokenRepo.findById(authToken.getToken())).thenReturn(Optional.of(authToken));

			// logout
			mvc.perform(post("/public/logout").param("token", authToken.getToken())).andExpect(status().isOk());
			
			Mockito.when(authTokenRepo.findById(authToken.getToken())).thenReturn(Optional.empty());

			assertEquals(HttpStatus.FORBIDDEN.value(), invokeSecuredEnpoint(user, authToken).getResponse().getStatus());

		} finally {
			if (user != null && user.getId() != null) {
				userRepo.delete(user);
			}
		}
	}

	@Test
	public void login_user_not_enabled() {

		User user = super.dataCreator.buildUser();

		try {
			user.setEnabled(false);
			userService.createUser(user, UserRole.CUSTOMER);

			securityController.login(user.getEmail(), "password");

			fail("Disabled user should not be allowed to login");
		} catch (ServiceException e) {
			assertEquals("user.not.enabled", e.getMessage());
		} finally {
			userRepo.delete(user);
		}
	}

	private MvcResult invokeSecuredEnpoint(User user, AuthToken authToken) throws Exception {

		return mvc.perform(get("/v1/users/{email}/properties", user.getEmail())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.header("authorization", authToken.getToken()))
				.andReturn();
	}

	private MvcResult invokeLoginEndpoint(User user, HttpStatus expectedStatus) {
		MvcResult result = null;
		try {
			result = mvc.perform(post("/public/login")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.param("userEmail", user.getEmail())
					.param("password", "password"))
					.andExpect(status().is(expectedStatus.value()))
					.andReturn();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
