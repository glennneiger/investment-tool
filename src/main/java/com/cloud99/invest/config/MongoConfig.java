package com.cloud99.invest.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.cloud99.invest.converters.ZonedDateTimeConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo", "com.cloud99.invest.domain" })
@Configuration
@PropertySource("classpath:application.properties")
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

    @Override
    public MongoClient mongoClient() {

		MongoCredential credential = MongoCredential.createCredential(userName, getDatabaseName(), password.toCharArray());
		ServerAddress address = new ServerAddress(hostName, port);

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		MongoClientOptions.Builder opts = new MongoClientOptions.Builder();
		opts.maxConnectionIdleTime(10000);
		opts.codecRegistry(pojoCodecRegistry);

		return new MongoClient(Arrays.asList(address), credential, opts.build());
    }


    @Override
	public CustomConversions customConversions() {
		converters.add(new ZonedDateTimeConverter());
		return new MongoCustomConversions(converters);
	}

	@Override
    protected String getDatabaseName() {
		return "investment";
    }

}
