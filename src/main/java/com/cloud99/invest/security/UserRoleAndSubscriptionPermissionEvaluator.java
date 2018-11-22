package com.cloud99.invest.security;

import com.cloud99.invest.domain.Subscription;
import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.MembershipType;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.services.UserService;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class UserRoleAndSubscriptionPermissionEvaluator implements PermissionEvaluator {

	private UserService userService;

	public UserRoleAndSubscriptionPermissionEvaluator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetMembership, Object permission) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) token.getPrincipal();

		User ourUser = userService.findUserByEmailAndValidate(user.getUsername());
		if (ourUser.getAuthorities().contains(UserRole.ADMIN)) {
			return true;
		}

		// is this request for MembershipType or UserRole?
		if (Subscription.SubscriptionType.SUBSCRIPTION_REF_NAME.equals(targetMembership)) {
			boolean hasAccess = ourUser.getMembershipType().equals(MembershipType.findByValue((String) permission));
			return hasAccess;
		} else if (UserRole.USER_ROLE_REF_NAME.equals(targetMembership)) {
			for (UserRole role : ourUser.getUserRoles()) {
				if (role.name().equals(permission)) {
					return true;
				}
			}
			return false;
		} else {
			throw new RuntimeException("You must define a targetMembership type for security annotations");
		}
		// check the users Membership to see if it matches the targetMembership

//		return ourUser.getAuthorities().stream().filter(a -> a.getAuthority().equals(targetDomainObject)).findFirst().isPresent();

	}

	public static void main(String[] args) {
		System.out.println(Subscription.SubscriptionType.class.getName());
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException(String.format("Security Method invoked but not implemented: {}", this.getClass().getName()));
	}

}
