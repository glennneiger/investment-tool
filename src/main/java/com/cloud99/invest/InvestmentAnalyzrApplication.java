
package com.cloud99.invest;

import com.cloud99.invest.config.AppConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@SpringBootApplication
@PropertySource("application-${spring.profiles.active}.properties")
@ComponentScan(basePackageClasses = { AppConfig.class, InvestmentAnalyzrApplication.class })
public class InvestmentAnalyzrApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InvestmentAnalyzrApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(InvestmentAnalyzrApplication.class, args);
	}
}
