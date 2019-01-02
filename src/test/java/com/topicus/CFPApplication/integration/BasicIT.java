package com.topicus.CFPApplication.integration;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;


@RunWith(Arquillian.class)
public class BasicIT {
	
	@Drone
	WebDriver driver;
	
	@Test
	public void testOpeningConferenceHomepage() {
		String url  = "http://api.topiconf.carpago.nl/api/presentationdraft";
		driver.get(url);
		
		String pageTitle = driver.getTitle();
		String test = driver.getPageSource();
		
//		System.out.println(pageTitle);
//		System.out.println(test);
		
		Assert.assertTrue(test.contains("AllSpark"));
		Assert.assertEquals(null, pageTitle);
	}

}
