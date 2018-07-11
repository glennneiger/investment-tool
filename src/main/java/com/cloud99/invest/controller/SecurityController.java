package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
public class SecurityController implements Controller {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/login", produces = JSON)
	public String login(@RequestParam String userEmail, @RequestParam String password) {

		return null;
	}
	@PostMapping(path = "/registration", produces = JSON, consumes = JSON)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User registerUserAndAccount(@RequestBody @Valid User user, @RequestParam String accountName, WebRequest request) {

		return userService.registerUserAndAccount(user, accountName, request.getContextPath());
	}

	@PostMapping(path = "/registrationConfirmation", produces = JSON)
	public User registrationConfirmation(@RequestParam String token) {

		return userService.confirmUserRegistration(token);
	}

	@PostMapping(path = "/login", produces = JSON, consumes = JSON)
	@ResponseBody
	public void login(@Valid User user, WebRequest request) {
		// TODO - NG - add login code
	}
}
