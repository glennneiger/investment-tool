package com.cloud99.invest.controller;

import com.cloud99.invest.domain.account.Account;
import com.cloud99.invest.domain.account.UserRole;
import com.cloud99.invest.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService acctService;

	@PostMapping("/")
	public Account createAccount(String accountName) {
		return acctService.createAccount(null, accountName, UserRole.FREE_USER);
	}

	@DeleteMapping
	public Long deleteByName(@RequestParam String name) {
		return acctService.deleteAccountByName(name);
	}
}
