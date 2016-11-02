package com.vector.service;

import java.util.List;

import com.vector.model.WkstWorkstation;

public interface WorkstationService {

	public void addWkst(WkstWorkstation wkst);
	public void updateWkst(WkstWorkstation wkst);
	public WkstWorkstation findByID(String id);
	public void deleteWkst(String id);
	public List<WkstWorkstation> getAll();
	public List<WkstWorkstation> findWithGroups();
}
