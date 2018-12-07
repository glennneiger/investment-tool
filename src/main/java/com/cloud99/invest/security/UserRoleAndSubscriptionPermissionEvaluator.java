package com.cloud99.invest.security;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.services.UserService;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class UserRoleAndSubscriptionPermissionEvaluator implements PermissionEvaluator {

	private final UserService userService;

	public UserRoleAndSubscriptionPermissionEvaluator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetMembershipOrRole, Object permission) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) token.getPrincipal();

		User ourUser = userService.findUserByEmailAndValidate(user.getUsername());
		if (ourUser.getAuthorities().contains(UserRole.ADMIN)) {
			return true;
		}

		// is this request for MembershipType or UserRole?
		if (MembershipType.MEMBERSHIP_TYPE_REF_NAME.equals(targetMembershipOrRole)) {
			boolean hasAccess = ourUser.getMembershipType().equals(MembershipType.findByValue((String) permission));
			return hasAccess;
		} else if (UserRole.USER_ROLE_REF_NAME.equals(targetMembershipOrRole)) {
			for (UserRole role : ourUser.getUserRoles()) {
				if (role.name().equals(permission)) {
					return true;
				}
			}
			return false;
		} else {
			throw new RuntimeException("You must define a targetMembership type for security annotations");
		}

	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException(String.format("Security Method invoked but not implemented: %s", this.getClass().getName()));
	}

}
