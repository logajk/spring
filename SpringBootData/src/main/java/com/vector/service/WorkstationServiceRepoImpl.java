package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.model.WkstWorkstation;
import com.vector.repo.WorkstationRepository;

@Service
@Transactional
public class WorkstationServiceRepoImpl implements WorkstationService {

	@Autowired
	private WorkstationRepository repository;
	
	public void addWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		repository.save(wkst);
	}

	public void updateWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		
		repository.save(wkst);
	}

	public WkstWorkstation findByID(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	public void deleteWkst(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	public List<WkstWorkstation> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public List<WkstWorkstation> findWithGroups() {
		// TODO Auto-generated method stub
		return repository.findWithGroup();
	}
}
