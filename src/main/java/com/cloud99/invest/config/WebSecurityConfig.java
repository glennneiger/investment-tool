package com.cloud99.invest.config;

import com.cloud99.invest.config.security.Cloud99BasicAuthEntryPoint;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalAuthentication
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Cloud99BasicAuthEntryPoint authenticationEntryPoint;

	@Autowired
	private UserService userService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// TODO - implement custom AuthenticationProvider
		auth.userDetailsService(userService);
		// auth.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
		.and().authorizeRequests().antMatchers("/**", "/registration").permitAll()
//		.and().authorizeRequests().antMatchers("/v1").authenticated()
		.and().httpBasic().authenticationEntryPoint(authenticationEntryPoint);

		// http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
	}
}
