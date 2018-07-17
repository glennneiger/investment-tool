package com.cloud99.invest.config;

import com.cloud99.invest.config.security.Cloud99BasicAuthEntryPoint;
import com.cloud99.invest.config.security.Cloud99SimpleUrlAuthenticationSuccessHandler;
import com.cloud99.invest.config.security.NoRedirectStrategy;
import com.cloud99.invest.config.security.TokenAuthenticationFilter;
import com.cloud99.invest.config.security.TokenAuthenticationProvider;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableGlobalAuthentication
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/public/**"));
	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/v1/**"));

	@Autowired
	private Cloud99BasicAuthEntryPoint authenticationEntryPoint;

	@Autowired
	private UserService userService;

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().requestMatchers(PUBLIC_URLS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userService);
		auth.authenticationProvider(tokenAuthenticationProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
        .antMatchers("/public/**").permitAll()
        .and()
		.authenticationProvider(tokenAuthenticationProvider())
		.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
		.exceptionHandling().defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
		.authorizeRequests().anyRequest().authenticated()
		.and().formLogin().disable()
		.httpBasic().disable()
		.logout().disable()
		.csrf().disable();
	}

	@Autowired
	private Cloud99SimpleUrlAuthenticationSuccessHandler successHandler;

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	@Bean
	public TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS, userService);
		filter.setAuthenticationManager(authenticationManager(null));
		filter.setAuthenticationSuccessHandler(successHandler);
		return filter;
	}

	/**
	 * Disable Spring boot automatic filter registration.
	 */
//	@Bean
//	FilterRegistrationBean disableAutoRegistration(final TokenAuthenticationFilter filter) {
//		final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//		registration.setEnabled(false);
//		return registration;
//	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new NoRedirectStrategy());
		return successHandler;
	}

	@Bean
	public AuthenticationProvider tokenAuthenticationProvider() {
		return new TokenAuthenticationProvider(userService); 
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationProvider provider) {
		return new ProviderManager(Arrays.asList(new TokenAuthenticationProvider(userService)));
	}
}
