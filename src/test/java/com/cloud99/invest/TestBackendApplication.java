package com.cloud99.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
public class TestBackendApplication {

	// TODO - NG - not sure this is needed for our integration tests!!!
	public static void main(String[] args) {
		SpringApplication.run(TestBackendApplication.class, args);
	}
}