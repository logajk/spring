package com.abalia.repo.linkedin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.abalia.model.linkedin.Linkedin;

public class LinkedinRepositoryImpl implements LinkedinRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Linkedin findById_demanda(String id_demanda) {
		// TODO Auto-generated method stub
		Query query = new Query();
		
		query.addCriteria(Criteria.where("id_demanda").is(id_demanda));
		
		return mongoTemplate.findOne(query, Linkedin.class);
	}
}
