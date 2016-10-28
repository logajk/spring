package com.vector.service;

import java.util.List;

import com.vector.model.WkstCounter;

public interface CounterService {

	public WkstCounter findById(long id);

	public List<WkstCounter> findAll();

	public void save(WkstCounter counter);

	public void delete(long id);
}