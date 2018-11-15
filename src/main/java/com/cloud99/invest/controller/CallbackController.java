package com.cloud99.invest.controller;

import com.cloud99.invest.dto.requests.PaymentRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Publicly exposed endpoints to support integration partner callbacks
 *
 */
@RestController
@RequestMapping(path = "/public")
public class CallbackController implements Controller {


	@PostMapping(path = "/address")
	public String address(@ModelAttribute PaymentRequest obj) {
		return "Endpoint invoked!";
	}
}
