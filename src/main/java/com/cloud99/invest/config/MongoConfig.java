package com.cloud99.invest.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.cloud99.invest.converters.ZonedDateTimeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo", "com.cloud99.invest.domain" })
@Configuration
@PropertySource("classpath:application.properties")
@Order(15)
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${mongo.host}")
	private String hostName;

	@Value("${mongo.port}")
	private Integer port;

	@Value("${mongo.user.name}")
	private String userName;

	@Value("${mongo.user.password}")
	private String password;

	private List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

	@Autowired
	private MongoDbFactory mongoFactory;

	@Autowired
	private MongoMappingContext mongoMappingContext;

	@Autowired
	private ObjectMapper objectMapper;

    @Override
    public MongoClient mongoClient() {

		MongoCredential credential = MongoCredential.createCredential(userName, "admin", password.toCharArray());
		ServerAddress address = new ServerAddress(hostName, port);

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		MongoClientOptions.Builder opts = new MongoClientOptions.Builder();
		opts.connectTimeout(10000);
		opts.maxConnectionIdleTime(5);
		opts.minConnectionsPerHost(5);
		opts.maxConnectionIdleTime(10000);
		opts.codecRegistry(pojoCodecRegistry);

		return new MongoClient(Arrays.asList(address), credential, opts.build());
    }


    @Override
	public CustomConversions customConversions() {
		converters.add(new ZonedDateTimeConverter());
		return new MongoCustomConversions(converters);
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext) {

			@Override
			public <S> S read(Class<S> clazz, Bson bson) {
				String string = JSON.serialize(bson);
				try {
					return objectMapper.readValue(string, clazz);
				} catch (IOException e) {
					throw new RuntimeException(string, e);
				}
			}

			@Override
			public void write(Object obj, Bson bson) {

				String string = null;
				try {
					string = objectMapper.writeValueAsString(obj);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(string, e);
				}

			}
		};

		// this is my customization
		mongoConverter.setMapKeyDotReplacement("_");
		mongoConverter.afterPropertiesSet();
		return mongoConverter;

	}

	@Override
    protected String getDatabaseName() {
		return "investment";
    }

}
