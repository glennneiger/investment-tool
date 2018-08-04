package com.cloud99.invest.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.cloud99.invest.converters.mongo.ZonedDateTimeConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories(basePackages = { "com.cloud99.invest.repo", "com.cloud99.invest.domain" })
@Configuration
@PropertySource("classpath:application.properties")
@Order(15)
@EnableTransactionManagement
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${mongo.host}")
	private String hostName;

	@Value("${mongo.port}")
	private int port;

	@Value("${mongo.user.name}")
	private String userName;

	@Value("${mongo.user.password}")
	private String password;

	private List<Converter<?, ?>> converters = new ArrayList<>();

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
		opts.connectTimeout(600000); // 10 mins
		opts.codecRegistry(pojoCodecRegistry);

		return new MongoClient(Arrays.asList(address), credential, opts.build());
    }

	@Bean
	public MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
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

	@SuppressWarnings("boxing")
	public static void printConnectionOptions(MongoClientOptions options) {
		StringBuffer msg = new StringBuffer();
		msg.append("MongoDB Connection Options ---\n" + LINE + "\n");
		msg.append(format("ApplicationName", options.getApplicationName()));
		msg.append(format("ConnectionsPerHost", options.getConnectionsPerHost()));
		msg.append(format("ConnectTimeout", options.getConnectTimeout()));
		msg.append(format("HeartbeatConnectTimeout", options.getHeartbeatConnectTimeout()));
		msg.append(format("HeartbeatFrequency", options.getHeartbeatFrequency()));
		msg.append(format("HeartbeatSocketTimeout", options.getHeartbeatSocketTimeout()));
		msg.append(format("LocalThreshold", options.getLocalThreshold()));
		msg.append(format("MaxConnectionIdleTime", options.getMaxConnectionIdleTime()));
		msg.append(format("MaxConnectionLifeTime", options.getMaxConnectionLifeTime()));
		msg.append(format("MaxWaitTime", options.getMaxWaitTime()));
		msg.append(format("MinConnectionsPerHost", options.getMinConnectionsPerHost()));
		msg.append(format("MinHeartbeatFrequency", options.getMinHeartbeatFrequency()));
		msg.append(format("ServerSelectionTimeout", options.getServerSelectionTimeout()));
		msg.append(format("SocketTimeout", options.getSocketTimeout()));
		msg.append(format("isSocketKeepAlive", options.isSocketKeepAlive()));
		msg.append(format("SslEnabled", options.isSslEnabled()));
		msg.append(format("ThreadsAllowedToBlockForConnectionMultiplier", options.getThreadsAllowedToBlockForConnectionMultiplier()));

		System.out.println(msg.toString());

		// LOGGER.info(msg.toString());
	}

	public static String PADDING = "%-40s%s\n";
	public static String LINE = "--------------------------------------------";

	public static String format(String name, Object val) {
		return String.format(PADDING, name, val) + LINE + "\n";

	}

}
