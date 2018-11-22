package com.cloud99.invest.domain.account;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

	CUSTOMER, ADMIN;

	public static final String USER_ROLE_REF_NAME = "UserRole";

	@Override
	public String getAuthority() {
		return this.name();
	}
}
