package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.model.WkstStatus;
import com.vector.repo.StatusRepository;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusRepository repository;
	
	public void addStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		repository.save(status);
	}

	public void updateStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		repository.save(status);
	}

	public void deleteStatus(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}


	public WkstStatus findByID(String id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	public List<WkstStatus> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}
