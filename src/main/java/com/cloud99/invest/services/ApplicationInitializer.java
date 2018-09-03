package com.cloud99.invest.services;

import com.cloud99.invest.domain.financial.ItemizedCost;
import com.cloud99.invest.repo.GenericRepo;
import com.cloud99.invest.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used during startup (after beans are constructed) to perform
 * any needed actions
 */
@Slf4j
@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {

	public static final String HOLDING_COSTS_JSON = "/static-data/holdingCostsRefData.json";
	public static final String EXPENCES_JSON = "/static-data/expensesCostsRefData.json";
	public static final String CLOSING_COSTS_JSON = "/static-data/closingCostsRefData.json";

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private ObjectMapper objMapper;

	@Autowired
	private GenericRepo genericRepo;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (!collectionExists(GenericRepo.HOLDING_COSTS_COLLECTION_NAME)) {
			log.info("Loading data for ref data collection: " + GenericRepo.HOLDING_COSTS_COLLECTION_NAME);
			loadRefData(HOLDING_COSTS_JSON, GenericRepo.HOLDING_COSTS_COLLECTION_NAME);
		}

		if (!collectionExists(GenericRepo.EXPENCES_COSTS_COLLECTION_NAME)) {
			log.info("Loading data for ref data collection: " + GenericRepo.EXPENCES_COSTS_COLLECTION_NAME);
			loadRefData(EXPENCES_JSON, GenericRepo.EXPENCES_COSTS_COLLECTION_NAME);
		}

		if (!collectionExists(GenericRepo.CLOSING_COSTS_COLLECTION_NAME)) {
			log.info("Loading data for ref data collection: " + GenericRepo.CLOSING_COSTS_COLLECTION_NAME);
			loadRefData(CLOSING_COSTS_JSON, GenericRepo.CLOSING_COSTS_COLLECTION_NAME);
		}
	}

	private boolean collectionExists(String collectionName) {
		return genericRepo.collectionExists(collectionName);
	}

	private void loadRefData(String jsonFileName, String collectionName) {

		try {
			String holdingCostsJson = fileUtil.getFileContents(jsonFileName);
			List<ItemizedCost> myObjects = objMapper.readValue(holdingCostsJson, new TypeReference<List<ItemizedCost>>() {});
			genericRepo.saveList(myObjects, collectionName);
		} catch (Exception e) {
			log.error("Error loading collection: " + collectionName + " from json file: " + jsonFileName + " msg:" + e.getMessage(), e);
		}
	}

}
