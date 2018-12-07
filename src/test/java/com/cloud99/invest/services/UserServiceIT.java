package com.cloud99.invest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.events.EventHandlingService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

public class UserServiceIT extends BaseIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private EventHandlingService eventHandlingMock;

	@BeforeAll
	public void beforeAll() {
		super.beforeAll();
	}

	@Test
	public void testRegistration() {

		// delete user and account created from the parents "beforeAll" method
		userRepo.delete(user);
		acctRepo.delete(account);
		user = null;
		account = null;

		AccountCreationRequest request = dataCreator.buildAccountCreationRequest();

		account = userService.registerUserAndAccount(request, "callbackUrl");

		// assert user attributes
		Optional<User> userOpt = userService.findUserById(account.getOwnerId());

		assertTrue(userOpt.isPresent());
		User returnUser = userOpt.get();
		assertTrue(returnUser.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.CUSTOMER.name())));
		assertEquals(request.getBirthDate(), returnUser.getBirthDate());
		assertEquals(request.getFirstName(), returnUser.getPersonName().getFirstName());
		assertEquals(request.getLastName(), returnUser.getPersonName().getLastName());
		assertEquals(request.getMiddleName(), returnUser.getPersonName().getMiddleName());
		assertEquals(request.getEmail(), returnUser.getEmail());
		assertEquals(request.getGender(), returnUser.getGender());
		assertNotEquals(request.getPassword(), returnUser.getPassword());

		// assert account attributes
		assertEquals(request.getAccountName(), account.getName());
		assertEquals(returnUser.getId(), account.getOwnerId());
		assertEquals(Status.PENDING, account.getStatus());
		assertNotNull(account.getCreateDate());
		assertNotNull(account.getGeneralSettings());
		assertNotNull(account.getGeneralSettings().getNumberOfPropertiesUserCanStore());
		assertEquals(request.getLocale(), account.getGeneralSettings().getLocale());
		List<Account> accts = acctRepo.findAll();
		assertEquals(1, accts.size());

	}

	@Test
	public void testUnauthorized() throws Exception {
		MvcResult result = mvc.perform(get("/v1/accounts/").header("authorization", "123456789").with(user("testUser"))).andReturn();

		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}
}
