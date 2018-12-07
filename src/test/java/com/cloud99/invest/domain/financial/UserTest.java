package com.cloud99.invest.domain.financial;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserTest extends MockitoTest {

	private User user;

	@BeforeEach
	public void beforeEach() {
		user = new User();
	}

	@Test
	public void testAddRole() {
		user.addUserRole(UserRole.ADMIN);

		assertTrue(user.getUserRoles().contains(UserRole.ADMIN));
	}

	@Test
	public void testGetAuthorities() {
		user.addUserRole(UserRole.ADMIN);
		user.addUserRole(UserRole.CUSTOMER);

		UserRole admin = UserRole.ADMIN;
		UserRole customer = UserRole.CUSTOMER;

		assertTrue(user.getAuthorities().contains(admin));
		assertTrue(user.getAuthorities().contains(customer));
	}

}
