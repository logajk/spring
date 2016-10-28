package com.vector.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstDevice;

@Repository
public class DeviceDAOImpl implements DeviceDAO {

	static final Logger LOGGER = Logger.getLogger(DeviceDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see com.vector.dao.DeviceDAO#findById(long)
	 */
	@Override
	public WkstDevice findById(long id){
		return (WkstDevice) sessionFactory.getCurrentSession().get(WkstDevice.class, id);
	}
	
	
	/* (non-Javadoc)
	 * @see com.vector.dao.DeviceDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstDevice> findAll(){
		return sessionFactory.getCurrentSession().createQuery("FROM WkstDevice w JOIN FETCH w.workStation").list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstDevice> findByLogicalName(String logicalName){
		return  sessionFactory.getCurrentSession().createQuery("FROM WkstDevice w WHERE w.logicalname LIKE :logicalname")
				.setString("logicalname", logicalName)
				.list();
	}
	
	@Override
	public void addDevice(WkstDevice device){
		sessionFactory.getCurrentSession().save(device);
	}
	
	@Override
	public void delete(long id){
		WkstDevice device = findById(id);
		
		if(device != null)
			sessionFactory.getCurrentSession().delete(device);
		
	}
}
