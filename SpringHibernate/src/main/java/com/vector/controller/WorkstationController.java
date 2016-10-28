package com.vector.controller;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vector.model.WkstWorkstation;
import com.vector.service.WorkstationService;

@Controller
@RequestMapping("/workstationService")
public class WorkstationController {

	@Autowired
	WorkstationService service;
	
	@RequestMapping(value="/findAll", method=RequestMethod.GET)
	@ResponseBody
	@JsonIgnore
	public List<WkstWorkstation> findAll(){
		return service.getAll();
	}
}
