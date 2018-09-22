package com.cloud99.invest.config;

import com.mongodb.MongoClient;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

@Profile("test-data")
@Configuration
public class TestMongoConfig {

	private static final String MONGO_DB_URL = "localhost";
	private static final String MONGO_DB_NAME = "embeded_db";

	public static EmbeddedMongoFactoryBean mongo;
	public static MongoClient mongoClient;

	@MockBean
	private MongoTemplate mongoTemplate;

	// @Bean
	// public MongoTemplate mongoTemplate() throws Exception {
	//
	// mongo = new EmbeddedMongoFactoryBean();
	// mongo.setBindIp(MONGO_DB_URL);
	// mongoClient = mongo.getObject();
	// MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
	//
	// return mongoTemplate;
	// }

	// @PreDestroy
	// public void destroy() {
	// mongoClient.close();
	// mongo.destroy();
	// }

}
