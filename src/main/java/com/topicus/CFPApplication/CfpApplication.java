package com.topicus.CFPApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CfpApplication {

	public static void main(String[] args) {
//		SpringApplication.run(CfpApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfpApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
}
