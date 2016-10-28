package com.vector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vector.model.WkstGroup;
import com.vector.service.GroupService;

@Controller
@RequestMapping("/groupService")
public class GroupController {

	@Autowired
	GroupService service;
	
	@RequestMapping(value="/findAll", method=RequestMethod.GET)
	@ResponseBody
	public List<WkstGroup> findAll(){
		return service.findAll();
	}
}
