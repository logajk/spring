package com.vector.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	static final Logger LOGGER = Logger.getLogger(HelloController.class);
	
	@RequestMapping("/")
	public String index(){
		
		LOGGER.debug("Estoy en index");
		
		return "Hola mundo!!!";
	}
}
