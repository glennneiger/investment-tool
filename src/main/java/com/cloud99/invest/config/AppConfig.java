package com.cloud99.invest.config;

import com.cloud99.invest.converters.json.CurrencyUnitDeserializer;
import com.cloud99.invest.converters.json.CurrencyUnitSerializer;
import com.cloud99.invest.converters.json.MoneySerializer;
import com.cloud99.invest.util.LoggingRequestInterceptor;
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

import org.bson.types.ObjectId;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = { "com.cloud99.invest" },
	excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.cloud99.invest.config.*"))
@PropertySource("classpath:application.properties")
@EnableWebMvc
@Order(1)
public class AppConfig extends WebMvcAutoConfiguration implements WebMvcConfigurer {

	public static final SimpleDateFormat UTC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS’Z");

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/**").addResourceLocations("/public/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
		b.indentOutput(true);
		b.configure(objectMapper());
		return b;
	}

	@SuppressWarnings("serial")
	@Bean
	public ObjectMapper objectMapper() {

		ObjectMapper mapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addSerializer(CurrencyUnit.class, new CurrencyUnitSerializer());
		module.addSerializer(Money.class, new MoneySerializer());
		module.addDeserializer(CurrencyUnit.class, new CurrencyUnitDeserializer());
		mapper.registerModule(module);

		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		UTC_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(UTC_DATE_FORMAT);

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
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("application.properties") };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(false);
		return pspc;
	}

	@Bean("restTemplate")
	public RestTemplate getRestClient() {

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;

	}

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}

	@Bean
	public Module jsonComponentModule() {
		return new JsonComponentModule();
	}

}
