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
		user.addUserRole(UserRole.PRO_USER);

		assertTrue(user.getUserRoles().contains(UserRole.PRO_USER));
	}

	@Test
	public void testGetAuthorities() {
		user.addUserRole(UserRole.ADMIN);
		user.addUserRole(UserRole.PRO_USER);

		SimpleGrantedAuthority admin = new SimpleGrantedAuthority(UserRole.ADMIN.name());
		SimpleGrantedAuthority pro = new SimpleGrantedAuthority(UserRole.PRO_USER.name());

		assertTrue(user.getAuthorities().contains(admin));
		assertTrue(user.getAuthorities().contains(pro));
	}

}
