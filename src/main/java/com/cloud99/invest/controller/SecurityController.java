package com.cloud99.invest.controller;

import com.cloud99.invest.domain.redis.AuthToken;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.services.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/public")
public class SecurityController implements Controller {

	@Autowired
	private SecurityService securityService;

	@PostMapping(path = "/login", produces = JSON_MEDIA_TYPE)
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
			// don't throw the exception as there is no reason to alert the user
		}
		securityService.logoutUser(token);
	}

	/**
	 * Register a new account and user
	 * 
	 * @param accountRequest
	 *            - a {@link AccountCreationRequest} which should the information
	 *            needed to create an account
	 * @param request
	 * @return an {@link AuthToken} if the account and user were successfully
	 *         created
	 */
	@PostMapping(path = "/registration", produces = JSON_MEDIA_TYPE, consumes = JSON_MEDIA_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public AuthToken registerUserAndAccount(@RequestBody @Validated AccountCreationRequest accountRequest, WebRequest request) {

		return securityService.registerUserAndAccount(accountRequest, request.getContextPath());
	}

	/**
	 * This endpoint is slated for a future release
	 * @param token
	 * @return
	 
	@PostMapping(path = "/registrationConfirmation", produces = JSON_MEDIA_TYPE)
	@ResponseStatus(HttpStatus.CREATED)
	public User registrationConfirmation(@RequestParam String token) {

		User user = securityService.confirmUserRegistration(token);
		securityService.createAuthToken(user.getId());
		return user;
	}
	*/
}
