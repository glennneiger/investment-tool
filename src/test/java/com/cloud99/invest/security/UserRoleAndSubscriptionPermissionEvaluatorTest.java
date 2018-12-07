package com.cloud99.invest.security;

import static org.junit.jupiter.api.Assertions.*;
import static com.cloud99.invest.domain.account.UserRole.USER_ROLE_REF_NAME;
import static com.cloud99.invest.domain.account.MembershipType.MEMBERSHIP_TYPE_REF_NAME;

import com.cloud99.invest.MockitoTest;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.domain.account.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserRoleAndSubscriptionPermissionEvaluatorTest extends MockitoTest {

	private UserRoleAndSubscriptionPermissionEvaluator evaluator;
	private User user;

	@BeforeEach
	public void setup() {
		super.setup();
		evaluator = new UserRoleAndSubscriptionPermissionEvaluator(userService);
		user = dataCreator.buildUser();
		mockUserServiceBehavior();
	}

	@Test
	public void testHasPermission_membershipType_paid_success() {
		user.setMembershipType(MembershipType.PAID);
		assertTrue(executeEvaluator(MEMBERSHIP_TYPE_REF_NAME, MembershipType.PAID.name()));
	}

	@Test
	public void testHasPermission_membershipType_paid_fail() {
		assertFalse(executeEvaluator(MEMBERSHIP_TYPE_REF_NAME, MembershipType.PAID.name()));
	}

	@Test
	public void testHasPermission_admin_paid_fail() {
		assertFalse(executeEvaluator(USER_ROLE_REF_NAME, UserRole.ADMIN.name()));
	}

	@Test
	public void testHasPermission_admin_role_success() {
		user.addUserRole(UserRole.ADMIN);
		assertTrue(executeEvaluator(USER_ROLE_REF_NAME, UserRole.ADMIN.name()));
	}

	@Test
	public void testHasPermission_admin_role_fail() {
		assertFalse(executeEvaluator(USER_ROLE_REF_NAME, UserRole.ADMIN.name()));
	}

	private boolean executeEvaluator(String targetMembershipOrRole, String permission) {
		org.springframework.security.core.userdetails.User ud = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(ud, "someToken");
		return evaluator.hasPermission(token, targetMembershipOrRole, permission);
	}

	private void mockUserServiceBehavior() {
		Mockito.when(userService.findUserByEmailAndValidate(Mockito.anyString())).thenReturn(user);
	}
}
