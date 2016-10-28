package com.vector.service;

import java.util.List;

import com.vector.model.WkstCommand;

public interface CommandService {

	public WkstCommand findById(int id);

	public List<WkstCommand> findAll();

	public List<WkstCommand> findByIdWithWkst(String id);

	public void save(WkstCommand command) throws Exception;

	public void delete(int id);
}