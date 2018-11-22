package com.cloud99.invest.controller;

import com.cloud99.invest.domain.Subscription;
import com.cloud99.invest.services.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/subscriptions")
public class SubscriptionController implements Controller {

	@Autowired
	private SubscriptionService subscriptionService;

	@GetMapping(path = "/", produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public List<Subscription> getActiveSubscriptions() {
		return subscriptionService.getAllActiveSubscriptions();
	}

}
