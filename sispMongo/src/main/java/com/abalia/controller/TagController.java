package com.abalia.controller;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abalia.model.tag.Tag;
import com.abalia.service.TagService;

@RestController
@RequestMapping("/tag")
@Api(name="Servicio de tags", description="Metodos para gestionar los tags, dichos tags se han recuperado de la p&aacute;gina StackOverflow", visibility=ApiVisibility.PUBLIC, stage=ApiStage.RC)
public class TagController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TagService service;
	
	@RequestMapping("/getTags")
	@ApiMethod(description="Devuelve todos los tags")
	public ResponseEntity<List<Tag>> getTags(){
		log.debug("Obteniendo TAGS.");
		return new ResponseEntity<List<Tag>>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ApiMethod(description="Inserta un nuevo tag")
	public ResponseEntity<List<Tag>> save(@RequestBody Tag tag){
		return new ResponseEntity<List<Tag>>(service.save(tag), HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ApiMethod(description="Borra un tag por id")
	public ResponseEntity<Tag> delete(@ApiQueryParam(name="id", description="Identificador del tag") @RequestParam(value="id") String id){
		
		Tag tag = service.findById(id);
		if(tag != null){
			service.delete(id);
			return new ResponseEntity<Tag>(tag, HttpStatus.OK);
		}else{
			return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
		}
	}
}
