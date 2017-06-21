package com.abalia;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class SispMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SispMongoApplication.class, args);
	}
}
