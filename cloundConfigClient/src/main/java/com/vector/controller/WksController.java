package com.vector.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vector.model.WkstWorkstation;
import com.vector.service.WorkstationService;

@RestController()
@RequestMapping("/workStationService")
public class WksController {

	static final Logger LOGGER = Logger.getLogger(WksController.class);
	
	@Autowired
	private WorkstationService service;
	
	
	@RequestMapping("/findAll")
	public List<WkstWorkstation> findAll(){
		return service.getAll();
	}
	
	@RequestMapping("/findAllWithGroup")
	public List<WkstWorkstation> findAllWithGroup(){
		return service.findWithGroups();
	}
	
	@RequestMapping("/findById/{id}")
	public WkstWorkstation findById(@PathVariable String id){
		return service.findByID(id);
	}
}
