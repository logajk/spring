package com.vector.dao;

import java.util.List;

import com.vector.model.WkstStatus;

public interface StatusDAO {

	public void addStatus(WkstStatus status);
	public void updateStatus(WkstStatus status);
	public void deleteStatus(String id);
	public WkstStatus findByID(String id);
	public List<WkstStatus> findAll();
}
