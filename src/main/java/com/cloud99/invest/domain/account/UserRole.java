package com.cloud99.invest.domain.account;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

	FREE_USER, PRO_USER, ADMIN;

	@Override
	public String getAuthority() {
		return this.name();
	}
}
