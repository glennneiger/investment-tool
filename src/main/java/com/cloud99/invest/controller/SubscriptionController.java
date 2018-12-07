package com.cloud99.invest.controller;

import com.cloud99.invest.domain.billing.Subscription;
import com.cloud99.invest.dto.requests.CreateSubscriptionRequest;
import com.cloud99.invest.dto.responses.ActiveSubscriptionResponse;
import com.cloud99.invest.services.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/v1")
public class SubscriptionController implements Controller {

	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping(path = "/users/{userId}/subscriptions", produces = JSON_MEDIA_TYPE, consumes = JSON_MEDIA_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void createSubscription(@RequestBody @Validated CreateSubscriptionRequest request) {
		subscriptionService.createSubscription(request);
	}

	@GetMapping(path = "/subscriptions/", produces = JSON_MEDIA_TYPE)
	@ResponseBody
	public List<ActiveSubscriptionResponse> getActiveSubscriptions() {
		List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
		if (subscriptions.isEmpty()) {
			return Collections.emptyList();
		}
		List<ActiveSubscriptionResponse> responses = new ArrayList<>(subscriptions.size());
		for (Subscription sub : subscriptions) {
			responses.add(ActiveSubscriptionResponse.buildFromSubscription(sub));
		}
		return responses;
	}

}
