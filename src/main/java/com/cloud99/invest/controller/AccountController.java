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
public class AccountController implements Controller {

	@Autowired
	private AccountService acctService;

	@PostMapping(path = "/{accountId}", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public Account updateAccount() {// @RequestBody Account account) {

		return acctService.updateAccount(null);
	}

	@PostMapping(path = "/", consumes = JSON_MEDIA_TYPE, produces = JSON_MEDIA_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Account createAccount(@RequestBody AccountCreationRequest request, Authentication auth) {

		return acctService.createAccount(request, (User) auth.getPrincipal());
	}

	@DeleteMapping(path = "/{accoutName}", consumes = TEXT_PLAIN_TYPE)
	public Long deleteByName(@RequestParam String accountName) {
		return acctService.deleteAccountByName(accountName);
	}
}
