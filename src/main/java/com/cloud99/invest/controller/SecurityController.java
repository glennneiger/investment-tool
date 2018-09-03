package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.services.SecurityService;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/public")
public class SecurityController implements Controller {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/login", produces = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public AuthToken login(@RequestParam String userEmail, @RequestParam String password) {

		return securityService.loginUser(userEmail, password);
	}

	@PostMapping(path = "/logout")
	public void logout(@RequestParam String token, HttpServletRequest request) {

		try {
			request.logout();
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		} catch (ServletException e) {
			log.error("Error occurred while logging out user from the HttpServletRequest: " + e.getMessage(), e);
		}
		securityService.logoutUser(token);
	}

	@PostMapping(path = "/registration", produces = JSON, consumes = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Account registerUserAndAccount(@RequestBody @Valid AccountCreationRequest accountRequest, WebRequest request) {

		return userService.registerUserAndAccount(accountRequest, request.getContextPath());
	}

	@PostMapping(path = "/registrationConfirmation", produces = JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public User registrationConfirmation(@RequestParam String token) {

		User user = securityService.confirmUserRegistration(token);
		securityService.createAuthToken(user.getId());
		return user;
	}
}
