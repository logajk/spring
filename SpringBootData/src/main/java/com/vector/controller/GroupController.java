package com.vector.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vector.model.WkstGroup;
import com.vector.repo.GroupRepository;

@RestController
@RequestMapping("/groupService")
public class GroupController {

	static final Logger LOGGER = Logger.getLogger(GroupController.class);
	
	@Autowired
	private GroupRepository repository;
	
	@RequestMapping("/findAll")
	public List<WkstGroup> findAll(){
		return repository.findAll();
	}
	
	@RequestMapping("/findById/{id}")
	public WkstGroup findById(@PathVariable int id){
		return repository.findOne(id);
	}
}
