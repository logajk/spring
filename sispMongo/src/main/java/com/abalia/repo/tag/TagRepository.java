package com.abalia.repo.tag;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.tag.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {

	public List<Tag> findByTagName(String TagName);
}
