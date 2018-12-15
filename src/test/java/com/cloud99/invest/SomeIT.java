package com.cloud99.invest;

import com.cloud99.invest.controller.AccountController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

//@ActiveProfiles("test")
//@RunWith(JUnitPlatform.class)
//@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
// @SpringBootTest(classes = TestBackendApplication.class, webEnvironment =
// SpringBootTest.WebEnvironment.RANDOM_PORT)
// @EnableAutoConfiguration

public class SomeIT extends BaseIntegrationTest {

	static {
		System.setProperty("spring.profiles.active", "test");
	}

	@Autowired
	private AccountController acct;

	@Test
	public void testSomething() {
		System.err.println("HERE");
	}
}
