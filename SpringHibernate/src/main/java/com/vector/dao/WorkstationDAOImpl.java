package com.vector.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstWorkstation;

@Repository
public class WorkstationDAOImpl implements WorkstationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		//Se rellena con espacios por la derecha.
		wkst.setWkstid(String.format("%1$-8s", wkst.getWkstid()));
		
		sessionFactory.getCurrentSession().save(wkst);
	}

	@Override
	public void updateWkst(WkstWorkstation wkst) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(wkst);
	}

	@Override
	public WkstWorkstation findByID(String id) {
		// TODO Auto-generated method stub
		return (WkstWorkstation) sessionFactory.getCurrentSession().get(WkstWorkstation.class, String.format("%1$-8s", id));
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		WkstWorkstation wkst = findByID(id);
		if(wkst != null)
			sessionFactory.getCurrentSession().delete(wkst);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WkstWorkstation> getAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from WkstWorkstation").list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstWorkstation> getAllWithDevices(){
		return sessionFactory.getCurrentSession().createQuery("from WkstWorkstation w JOIN FETCH w.devices").list();
	}
	
	@Override
	public WkstWorkstation findByIdWithDevices(String id){
		
		return (WkstWorkstation) sessionFactory.getCurrentSession()
				.createQuery("from WkstWorkstation w JOIN FETCH w.devices WHERE w.wkstid like :id")
				.setString("id", String.format("%1$-8s", id))
				.uniqueResult();
	}
	
	@Override
	public WkstWorkstation findByIdWithStatusReason(String id){
		return (WkstWorkstation) sessionFactory.getCurrentSession()
				.createQuery("from WkstWorkstation w JOIN FETCH w.statusreasonId WHERE w.wkstid like :id")
				.setString("id", String.format("%1$-8s", id))
				.uniqueResult();
	}
	
	@Override
	public WkstWorkstation findByIdWithCounters(String id){
		return (WkstWorkstation) sessionFactory.getCurrentSession()
				.createQuery("from WkstWorkstation w JOIN FETCH w.counters WHERE w.wkstid like :id")
				.setString("id", String.format("%1$-8s", id))
				.uniqueResult();
	}
}
