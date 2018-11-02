package com.cloud99.invest.security;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.services.UserService;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class RoleTypePermissionEvaluator implements PermissionEvaluator {

	private UserService userService;

	public RoleTypePermissionEvaluator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) token.getPrincipal();

		User ourUser = userService.findUserByEmailAndValidate(user.getUsername());
		boolean result = ourUser.getAuthorities().stream().filter(a -> a.getAuthority().equals(targetDomainObject)).findFirst().isPresent();
		return result;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException(String.format("Security Method invoked but not implemented: {}", this.getClass().getName()));
	}

}
