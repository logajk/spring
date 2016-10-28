package com.vector.dao;

import java.util.List;

import com.vector.model.WkstCounter;

public interface CounterDAO {

	public WkstCounter findById(long id);

	public List<WkstCounter> findAll();

	public void save(WkstCounter counter);

	public void delete(long id);
}