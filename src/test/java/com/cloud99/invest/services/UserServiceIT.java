package com.cloud99.invest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.cloud99.invest.BaseIntegrationTest;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.events.EventHandlingService;
import com.cloud99.invest.events.OnRegistrationRequestEvent;
import com.cloud99.invest.repo.AccountRepo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserServiceIT extends BaseIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepo acctRepo;

	@MockBean
	private EventHandlingService eventHandlingMock;

	@Test
	public void testRegistration() {
		User user = dataCreator.buildUser();
		String acctName = "TestAccountName";

		User returnUser = userService.registerUserAndAccount(user, acctName, null);

		assertNotNull(returnUser);
		assertTrue(returnUser.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.FREE_USER.name())));
		List<Account> accts = acctRepo.findAll();
		assertEquals(1, accts.size());

		verify(eventHandlingMock, times(1)).registrationRequestHandler(any(OnRegistrationRequestEvent.class));
	}

	@Test(expected = AccessDeniedException.class)
	public void testAccessDeniedException_expected() throws Exception {
		mvc.perform(get("/v1/accounts/").header("authorization", "123456789").with(user("testUser")));
	}
}
