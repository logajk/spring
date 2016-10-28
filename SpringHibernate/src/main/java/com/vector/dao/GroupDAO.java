package com.vector.dao;

import java.util.List;

import com.vector.model.WkstGroup;

public interface GroupDAO {

	public WkstGroup findById(int id);
	public List<WkstGroup> findAll();
}
