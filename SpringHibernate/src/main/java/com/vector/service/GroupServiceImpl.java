package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.dao.GroupDAO;
import com.vector.model.WkstGroup;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDAO groupDAO;
	
	@Override
	public WkstGroup findById(int id) {
		// TODO Auto-generated method stub
		return groupDAO.findById(id);
	}

	@Override
	public List<WkstGroup> findAll() {
		// TODO Auto-generated method stub
		return groupDAO.findAll();
	}
}
