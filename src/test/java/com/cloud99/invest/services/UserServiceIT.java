package com.cloud99.invest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.domain.Status;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.events.EventHandlingService;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.repo.AccountRepo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

public class UserServiceIT extends BaseIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepo acctRepo;

	@MockBean
	private EventHandlingService eventHandlingMock;

	@MockBean
	private ApplicationEventPublisher eventPublisher;

	@BeforeAll
	public void beforeAll() {
		super.beforeAll();
		userService.setApplicationEventPublisher(eventPublisher);
	}

	@Test
	public void testRegistration() {

		AccountCreationRequest request = dataCreator.buildAccountCreationRequest();

		Account account = userService.registerUserAndAccount(request, "callbackUrl");

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
		assertEquals(request.getLocale(), returnUser.getLocale());
		assertNotEquals(request.getPassword(), returnUser.getPassword());

		// assert account attributes
		assertEquals(request.getAccountName(), account.getName());
		assertEquals(returnUser.getId(), account.getOwnerId());
		assertEquals(Status.PENDING, account.getStatus());
		assertNotNull(account.getCreateDate());
		assertNotNull(account.getAccountOptions());
		assertNotNull(account.getAccountOptions().getTotalDocsAllowed());
		List<Account> accts = acctRepo.findAll();
		assertEquals(1, accts.size());

		verify(eventPublisher, times(1)).publishEvent(any(OnRegistrationRequestEvent.class));
	}

	@Test
	public void testUnauthorized() throws Exception {
		MvcResult result = mvc.perform(get("/v1/accounts/").header("authorization", "123456789").with(user("testUser"))).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}
}
