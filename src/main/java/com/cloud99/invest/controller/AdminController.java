package com.cloud99.invest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/admin")
public class AdminController implements Controller {

	@PostMapping(path = "/billing/plans", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createBillingPlan(Authentication auth) {

		// TODO - NG - do we need this or not?
	}
}
