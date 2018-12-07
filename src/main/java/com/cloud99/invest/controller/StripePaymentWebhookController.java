package com.cloud99.invest.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Publicly exposed endpoints to support integration partner callbacks
 *
 */
@RestController
@RequestMapping(path = "/public/stripe")
public class StripePaymentWebhookController implements Controller {


	@PostMapping(path = "/address")
	public String address(@ModelAttribute Object obj) {
		return "Endpoint invoked!";
	}
}
