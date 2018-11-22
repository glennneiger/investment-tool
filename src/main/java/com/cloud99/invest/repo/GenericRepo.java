package com.cloud99.invest.repo;

import com.cloud99.invest.domain.MongoDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repo is not tied to a specific collection/domain object type. It
 * utilizes a {@link MongoTemplate} directly and therefore can be utilized for
 * any collection.
 */
@Repository
public class GenericRepo {

	public static final String HOLDING_COSTS_COLLECTION_NAME = "holdingCostsRefData";
	public static final String EXPENCES_COSTS_COLLECTION_NAME = "expenseCostsRefData";
	public static final String CLOSING_COSTS_COLLECTION_NAME = "closingCostsRefData";
	public static final String SELLING_COSTS_COLLECTION_NAME = "sellingCostsRefData";

	@Autowired
	private MongoTemplate mongoTemplate;

	public boolean collectionExists(String collectionName) {
		return mongoTemplate.collectionExists(collectionName);
	}

	public <T extends MongoDocument> void saveList(List<T> items, String collectionName) {
		mongoTemplate.insert(items, collectionName);
	}

	public <T extends MongoDocument> List<T> getCollectionList(Class<T> clazz, String collectionName) {
		return mongoTemplate.findAll(clazz, collectionName);
	}
}
