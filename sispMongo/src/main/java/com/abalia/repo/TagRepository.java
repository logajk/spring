package com.abalia.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {

	public List<Tag> findByTagName(String TagName);
}
