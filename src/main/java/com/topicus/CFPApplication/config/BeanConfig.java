package com.topicus.CFPApplication.config;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean
	public Set<String> categories() {
		Set<String> categories = new TreeSet<String>((o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase()));
		return categories;
	}

}
