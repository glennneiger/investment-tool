package com.cloud99.invest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@TestPropertySource("classpath:application.properties")
public interface BaseSpringIntegrationTest {

	// @Value("${zillow.id}")
	// private String zillowId;


}
