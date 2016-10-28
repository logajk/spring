package com.vector;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@EnableJpaRepositories
public class SpringBootDataApplication {

	static final Logger LOGGER = Logger.getLogger(SpringBootDataApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootDataApplication.class, args);
	}
}
