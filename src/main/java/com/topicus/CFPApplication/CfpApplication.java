package com.topicus.CFPApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CfpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfpApplication.class, args);
//		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfpApplication.class);
//		builder.headless(false);
//		@SuppressWarnings("unused")
//		ConfigurableApplicationContext context = builder.run(args);
	}
}
