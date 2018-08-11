package com.cloud99.invest.security;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	private UserService userService;

	public TokenAuthenticationFilter(final RequestMatcher requiresAuth, UserService userService) {
		super(requiresAuth);
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.trace("Attempting to authenticate request");
		final String authToken = request.getHeader("authorization");

		final User user = userService.findAndValidateUserByAuthToken(authToken);
		UserDetails ud = userService.loadUserByUsername(user.getEmail());
		final Authentication auth = new UsernamePasswordAuthenticationToken(ud, authToken);
		return getAuthenticationManager().authenticate(auth);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		userService.updateUserTokenExpireTime((String) authResult.getCredentials());
		chain.doFilter(request, response);
	}

}
