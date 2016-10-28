package com.vector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vector.model.WkstStatus;
import com.vector.service.StatusService;


@Controller
@RequestMapping("/statusService")
public class StatusController {
	
	@Autowired
	StatusService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public WkstStatus findStatus(@PathVariable("id") String id){
		
		return service.findByID(id);
	}
	
	@RequestMapping(value="/findAll", method=RequestMethod.GET)
	@ResponseBody
	public List<WkstStatus> findAll(){
		return service.findAll();
	}
}
