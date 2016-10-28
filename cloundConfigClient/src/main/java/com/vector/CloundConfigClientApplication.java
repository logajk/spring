package com.vector;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloundConfigClientApplication {

	static final Logger LOGGER = Logger.getLogger(CloundConfigClientApplication.class);
		
	public static void main(String[] args) {
		SpringApplication.run(CloundConfigClientApplication.class, args);
	}
}
