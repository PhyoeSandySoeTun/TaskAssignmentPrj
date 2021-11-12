package com.mab.task.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
	
	private Map<String,String> prod;
	private Map<String,String> uat;
 
	
	public Map<String, String> getProd() {
		return prod;
	}

	public void setProd(Map<String, String> prod) {
		this.prod = prod;
	}

	public Map<String, String> getUat() {
		return uat;
	}

	public void setUat(Map<String, String> uat) {
		this.uat = uat;
	}
}
