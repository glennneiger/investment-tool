package com.cloud99.invest.controller;

import com.cloud99.invest.domain.User;
import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.dto.requests.AccountCreationRequest;
import com.cloud99.invest.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService acctService;

	@PostMapping("/")
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Account createAccount(@RequestBody AccountCreationRequest request, Authentication auth) {

		return acctService.createAccount(request, (User) auth.getPrincipal());
	}

	@DeleteMapping
	public Long deleteByName(@RequestParam String name) {
		return acctService.deleteAccountByName(name);
	}
}
