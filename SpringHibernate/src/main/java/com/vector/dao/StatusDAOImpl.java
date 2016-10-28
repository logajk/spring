package com.vector.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstStatus;

@Repository
public class StatusDAOImpl implements StatusDAO {

	static final Logger LOGGER = Logger.getLogger(StatusDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(status);
	}

	@Override
	public void updateStatus(WkstStatus status) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(status);
	}

	@Override
	public void deleteStatus(String id) {
		// TODO Auto-generated method stub
		WkstStatus status = findByID(id);
		
		if(status != null)
			sessionFactory.getCurrentSession().delete(status);
		else
			LOGGER.error("Error al borrar el estado: "+id);
	}

	@Override
	public WkstStatus findByID(String id) {
		// TODO Auto-generated method stub
		return (WkstStatus) sessionFactory.getCurrentSession().get(WkstStatus.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WkstStatus> findAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("FROM WkstStatus").list();
	}
}
