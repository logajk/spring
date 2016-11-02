package com.vector.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	static final Logger LOGGER = Logger.getLogger(MessageController.class);
	
	@Value("${config.key}")
	private String key;
	
	@RequestMapping("/message")
	public String getMessage(){
		return this.key;
	}
}
