package com.cloud99.invest.container;

import com.cloud99.invest.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for initializing all static data related to payment
 * including registering payment domain objects with payment integration
 * services
 */
@Slf4j
@Component
public class PaymentDataInitializer implements ApplicationListener<ContextStartedEvent> {

	@Autowired
	private PaymentService paymentService;

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		log.debug("Starting to initialize payment data");

	}

}
