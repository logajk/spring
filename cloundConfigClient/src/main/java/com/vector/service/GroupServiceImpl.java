package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.model.WkstGroup;
import com.vector.repo.GroupRepository;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository repository;
	
	public WkstGroup findById(int id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	public List<WkstGroup> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}
