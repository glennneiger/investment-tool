package com.cloud99.invest.config;

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
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = { "com.cloud99.invest" })
@PropertySource("classpath:application.properties")
@EnableWebMvc
@Order(1)
public class AppConfig {

	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS’Z");

	@Bean
	public ObjectMapper objectMapper() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(df);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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

	@Bean("restTemplate")
	public RestTemplate getRestClient() {

		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("application.properties") };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(false);
		return pspc;
	}

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}

	@Bean
	public Module jsonComponentModule() {
		return new JsonComponentModule();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("nickgilas@gmail.com");
		mailSender.setPassword("Cooper2017");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
