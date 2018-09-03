package com.cloud99.invest.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

// TODO - NG - I don't think this class is needed and we can delete it and remove the usage in the WebSecurityConfig
@Component
public class Cloud99BasicAuthEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try (PrintWriter writer = response.getWriter()) {
			writer.println("HTTP Status 401 - " + authException.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("cloud99");
		super.afterPropertiesSet();
	}

}
