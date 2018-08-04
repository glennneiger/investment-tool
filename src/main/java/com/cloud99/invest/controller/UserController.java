package com.cloud99.invest.controller;

import com.cloud99.invest.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController implements Controller {

	@Autowired
	private UserService userService;

	@DeleteMapping()
	public void deleteUser(@RequestParam String userId) {
		userService.deleteUser(userId);
	}

}
