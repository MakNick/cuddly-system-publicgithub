package com.topicus.CFPApplication.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * deze wordt niet meer gebruikt. Wellicht wel in de toekomst, dus laat ik het even staan.
 */

@Configuration
@ConfigurationProperties("spring.mail")
public class MailConfig {

	private String host, port, username, password;

	public List<String> getAllValues() {
		List<String> valueList = new ArrayList<>();
		Collections.addAll(valueList, "Host: " + host, "port: " + port, "username: " + username,
				"password: " + password);
		return valueList;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
