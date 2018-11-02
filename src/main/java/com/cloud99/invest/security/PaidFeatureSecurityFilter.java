package com.cloud99.invest.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class PaidFeatureSecurityFilter {

	public void filter(Authentication authentication, Object domainObj) {
		System.out.println(domainObj);
	}
}
