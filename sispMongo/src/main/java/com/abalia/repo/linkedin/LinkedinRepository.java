package com.abalia.repo.linkedin;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.linkedin.Linkedin;

public interface LinkedinRepository extends MongoRepository<Linkedin, String>, LinkedinRepositoryCustom {

	
}
