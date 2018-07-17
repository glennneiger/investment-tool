package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping(path = "/public")
public class SecurityController implements Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

	@Autowired
	private UserService userService;

	@PostMapping(path = "/login", produces = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public AuthToken login(@RequestParam String userEmail, @RequestParam String password) {

		return userService.loginUser(userEmail, password);
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
			LOGGER.error("Error occurred while logging out user from the HttpServletRequest: " + e.getMessage(), e);
		}
		userService.logoutUser(token);
	}

	@PostMapping(path = "/registration", produces = JSON, consumes = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User registerUserAndAccount(@RequestBody @Valid User user, @RequestParam String accountName, WebRequest request) {

		return userService.registerUserAndAccount(user, accountName, request.getContextPath());
	}

	@PostMapping(path = "/registrationConfirmation", produces = JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public User registrationConfirmation(@RequestParam String token) {

		User user = userService.confirmUserRegistration(token);
		userService.createAuthToken(user.getEmail());
		return user;
	}
}
