package com.cloud99.invest.config;

import com.cloud99.invest.ExcludeFromTest;
import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.cloud99.invest.converters.json.MoneySerializer;
import com.cloud99.invest.events.EventHandlingService;
import com.cloud99.invest.repo.redis.AuthTokenRepo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.apache.logging.log4j.core.config.Order;
import org.bson.types.ObjectId;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.TimeZone;

@ActiveProfiles({ "test" })
@EnableWebMvc
// @OverrideAutoConfiguration(enabled = true)
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
@ComponentScan(basePackages = { "com.cloud99.invest" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromTest.class))
@Order(1)
public class TestAppConfig {

	protected AuthTokenRepo authTokenRepoMock;

	@Bean
	public ObjectMapper objectMapper() {

		ObjectMapper mapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addSerializer(CurrencyUnit.class, new CurrencyUnitSerializer());
		module.addSerializer(Money.class, new MoneySerializer());
		module.addDeserializer(CurrencyUnit.class, new CurrencyUnitDeserializer());
		mapper.registerModule(module);

		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		AppConfig.UTC_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(AppConfig.UTC_DATE_FORMAT);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(jodaModule());
		mapper.registerModule(jsonComponentModule());
		mapper.registerModule(new SimpleModule() {
			{
				addDeserializer(ObjectId.class, new JsonDeserializer<ObjectId>() {
					@Override
					public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
						TreeNode oid = p.readValueAsTree().get("$oid");
						String string = oid.toString().replaceAll("\"", "");

						return new ObjectId(string);
					}
				});
			}
		});
		return mapper;
	}

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}

	@Bean
	public Module jsonComponentModule() {
		return new JsonComponentModule();
	}

//	@Bean
//	public MongoTemplate mongoTemplate() {
//		MongoTemplate t = Mockito.mock(MongoTemplate.class);
//		MongoConverter converter = Mockito.mock(MongoConverter.class);
//		Mockito.when(t.getConverter()).thenReturn(converter);
//		Mockito.when(converter.getMappingContext()).thenReturn(new MongoMappingContext());
//		return t;
//	}

//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//		Resource[] resources = new ClassPathResource[] { new ClassPathResource("application-${spring.profiles.active}.properties") };
//		pspc.setLocations(resources);
//		pspc.setIgnoreUnresolvablePlaceholders(true);
//		return pspc;
//	}

	@Bean
	public EventHandlingService eventHandlingService() {
		return Mockito.spy(EventHandlingService.class);
	}

	@Bean
	public AuthTokenRepo authTokenRepo() {
		if (authTokenRepoMock == null) {
			authTokenRepoMock = Mockito.mock(AuthTokenRepo.class);
		}
		return authTokenRepoMock;
	}

}
