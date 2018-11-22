package com.cloud99.invest.container;

import com.cloud99.invest.domain.Subscription;
import com.cloud99.invest.repo.GenericRepo;
import com.cloud99.invest.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for initializing all static data related to payment
 * including registering payment domain objects with payment integration
 * services
 */
@Slf4j
@Component
public class PaymentDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

	// s = env name (i.e local, dev, production)
	private static final String SUBSCRIPTION_JSON_FILE_NAME = "/static-data/%s-billing-subscriptions.json";
	private static final String SUBSCRIPTION_COLLECTION_NAME = Subscription.class.getSimpleName().toLowerCase();

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Autowired
	private GenericRepo genericRepo;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private ObjectMapper objMapper;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (!collectionExists(SUBSCRIPTION_COLLECTION_NAME)) {
			loadRefData(String.format(SUBSCRIPTION_JSON_FILE_NAME, activeProfile), SUBSCRIPTION_COLLECTION_NAME);
		} else {
			log.info("No Payment data to load, it already exists");
		}

	}

	private boolean collectionExists(String collectionName) {
		return genericRepo.collectionExists(collectionName);
	}

	private void loadRefData(String jsonFileName, String collectionName) {

		try {
			String holdingCostsJson = fileUtil.getFileContents(jsonFileName);
			List<Subscription> myObjects = objMapper.readValue(holdingCostsJson, new TypeReference<List<Subscription>>() {
			});

			genericRepo.saveList(myObjects, collectionName);
			log.info("Loaded: {} subscriptions into collection: {}", myObjects.size(), SUBSCRIPTION_COLLECTION_NAME);
		} catch (Exception e) {
			log.error("Error loading collection: " + collectionName + " from json file: " + jsonFileName + " msg: " + e.getMessage(), e);
		}
	}

}
