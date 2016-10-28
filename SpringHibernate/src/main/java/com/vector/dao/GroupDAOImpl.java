package com.vector.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstGroup;

@Repository
public class GroupDAOImpl implements GroupDAO {

	static final Logger LOGGER = Logger.getLogger(GroupDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public WkstGroup findById(int id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Estoy en findById");
		
		return (WkstGroup) sessionFactory.getCurrentSession().get(WkstGroup.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WkstGroup> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("FROM WkstGroup").list();
	}
}
