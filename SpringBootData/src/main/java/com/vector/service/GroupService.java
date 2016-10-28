package com.vector.service;

import java.util.List;

import com.vector.model.WkstGroup;

public interface GroupService {

	public WkstGroup findById(int id);
	public List<WkstGroup> findAll();
}
