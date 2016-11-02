package com.vector.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vector.model.WkstStatus;
import com.vector.repo.StatusRepository;

@RestController
@RequestMapping("/statusService")
public class StatusController {
	
	static final Logger LOGGER = Logger.getLogger(StatusController.class);
	
	@Autowired
	private StatusRepository repository;

	@RequestMapping("/findAll")
	public List<WkstStatus> findAll(){
		return repository.findAll();
	}
	
	@RequestMapping("/findById/{id}")
	public WkstStatus findById(@PathVariable String id){
		return repository.findOne(id);
	}
	
	@RequestMapping("/insert/{id}/{name}")
	public String  insert(@PathVariable String id, @PathVariable String name){
		
		WkstStatus status = new WkstStatus(id, name);
		
		try {
			repository.saveAndFlush(status);
			return "Operación realizada correctamente";
		} catch (Exception e) {
			// TODO: handle exception
			return "Se ha producido un error al guardar el registro";
		}		
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable String id){
		
		try {
			repository.delete(id);
			return "Operación realizada correctamente";
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getCause());
			return "Se ha producido un error al guardar el registro";
		}
	}
}
