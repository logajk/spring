package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.dao.StatusDAO;
import com.vector.model.WkstStatus;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusDAO statusDAO;
	
	@Override
	public void addStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		statusDAO.addStatus(status);
	}

	@Override
	public void updateStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		statusDAO.updateStatus(status);
	}

	@Override
	public void deleteStatus(String id) {
		// TODO Auto-generated method stub
		statusDAO.deleteStatus(id);
	}

	@Override
	public WkstStatus findByID(String id) {
		// TODO Auto-generated method stub
		return statusDAO.findByID(id);
	}

	@Override
	public List<WkstStatus> findAll() {
		// TODO Auto-generated method stub
		return statusDAO.findAll();
	}
}
