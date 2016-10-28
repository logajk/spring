package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.dao.WorkstationDAO;
import com.vector.model.WkstWorkstation;

@Service
@Transactional
public class WorkstationServiceImpl implements WorkstationService {

	@Autowired
	private WorkstationDAO workstationDAO;
	
	@Override
	public void addWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		workstationDAO.addWkst(wkst);
	}

	@Override
	public void updateWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		workstationDAO.updateWkst(wkst);
	}

	@Override
	public WkstWorkstation findByID(String id) {
		// TODO Auto-generated method stub
		return workstationDAO.findByID(id);
	}

	@Override
	public void deleteWkst(String id) {
		// TODO Auto-generated method stub
		workstationDAO.delete(id);
	}

	@Override
	public List<WkstWorkstation> getAll() {
		// TODO Auto-generated method stub
		return workstationDAO.getAll();
	}
	
	@Override
	public List<WkstWorkstation> getAllWithDevices(){
		return workstationDAO.getAllWithDevices();
	}
	
	@Override
	public WkstWorkstation findByIdWithDevices(String id){
		return workstationDAO.findByIdWithDevices(id);
	}
	
	@Override
	public WkstWorkstation findByIdWithStatusReason(String id){
		return workstationDAO.findByIdWithStatusReason(id);
	}
	
	@Override
	public WkstWorkstation findByIdWithCounters(String id){
		return workstationDAO.findByIdWithCounters(id);
	}
}
