package com.cloud99.invest.controller;

import com.cloud99.invest.repo.AccountRepo;
import com.cloud99.invest.repo.UserRepo;
import com.cloud99.invest.repo.VerificationTokenRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountRepo acctRepo;

	@Autowired
	private VerificationTokenRepo tokenRepo;

	@DeleteMapping
	public void deleteAll() {
		acctRepo.deleteAll();
		userRepo.deleteAll();
		tokenRepo.deleteAll();
	}

}
