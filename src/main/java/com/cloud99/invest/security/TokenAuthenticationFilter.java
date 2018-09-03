package com.cloud99.invest.security;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.exceptions.ServiceException;
import com.cloud99.invest.services.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	private SecurityService securityService;

	public TokenAuthenticationFilter(final RequestMatcher requiresAuth, SecurityService securityService) {
		super(requiresAuth);
		this.securityService = securityService;
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.trace("Attempting to authenticate request");
		final String authToken = request.getHeader("authorization");
		UserDetails ud = null;
		try {
			final User user = securityService.findAndValidateUserByAuthToken(authToken);
			ud = securityService.loadUserByUsername(user.getEmail());
		} catch (ServiceException e) {
			throw new PreAuthenticatedCredentialsNotFoundException(e.getErrorMessageCode());
		}
		final Authentication auth = new UsernamePasswordAuthenticationToken(ud, authToken);
		return auth;
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		securityService.updateUserForSuccessfulAuthentication((String) authResult.getCredentials());

		chain.doFilter(request, response);
	}

}
