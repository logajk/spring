package com.vector.dao;

import java.util.List;

import com.vector.model.WkstCommand;

public interface CommandDAO {

	public WkstCommand findById(int id);

	public List<WkstCommand> findAll();

	public List<WkstCommand> findByIdWithWkst(String id);

	public void save(WkstCommand command);

	public void delete(int id);

}