package com.cloud99.invest.config;

import com.cloud99.invest.security.Cloud99BasicAuthEntryPoint;
import com.cloud99.invest.security.Cloud99SimpleUrlAuthenticationSuccessHandler;
import com.cloud99.invest.security.TokenAuthenticationFilter;
import com.cloud99.invest.security.TokenAuthenticationProvider;
import com.cloud99.invest.services.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@EnableGlobalAuthentication
@Configuration
@ComponentScan(basePackages = { "com.cloud99.invest" })
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String PUBLIC_URLS = "/public/**";
	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/v1/**"));

	@Autowired
	private Cloud99BasicAuthEntryPoint authenticationEntryPoint;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private Cloud99SimpleUrlAuthenticationSuccessHandler successHandler;

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(securityService);
		builder.authenticationProvider(tokenAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
		.anonymous()
		.and()
		.authorizeRequests()
				.antMatchers("/v1/**").authenticated()
				.antMatchers("/v1/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(PUBLIC_URLS).permitAll()
        .and()
		.authenticationProvider(tokenAuthenticationProvider())
		.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
				.exceptionHandling().defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
				.and()
				// .authorizeRequests().anyRequest().authenticated()
				.formLogin().disable()
		.httpBasic()
		.authenticationEntryPoint(authenticationEntryPoint)
		.disable()
		.logout().disable()
		.csrf().disable();
//		.rememberMe();
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	@Bean
	public TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS, securityService);
		filter.setAuthenticationManager(authenticationManager(tokenAuthenticationProvider()));
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

//	@Bean
//	public SimpleUrlAuthenticationSuccessHandler successHandler() {
//		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
//		successHandler.setRedirectStrategy(new NoRedirectStrategy());
//		return successHandler;
//	}

	@Bean
	public AuthenticationProvider tokenAuthenticationProvider() {
		return new TokenAuthenticationProvider(securityService);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationProvider provider) {
		ProviderManager mgr = new ProviderManager(Arrays.asList(provider));
		mgr.setEraseCredentialsAfterAuthentication(false);
		return mgr;
	}
}
