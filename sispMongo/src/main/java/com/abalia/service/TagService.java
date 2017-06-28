package com.abalia.service;

import java.util.List;

import com.abalia.model.tag.Tag;

public interface TagService {

	List<Tag> findAll();

	List<Tag> findByTagName(String TagName);

	List<Tag> save(Tag tag);

	void delete(String id);

	Tag findById(String id);

}