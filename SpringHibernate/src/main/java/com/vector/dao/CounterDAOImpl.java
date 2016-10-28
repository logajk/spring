package com.vector.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstCounter;

@Repository
public class CounterDAOImpl implements CounterDAO {

	static final Logger LOGGER = Logger.getLogger(DeviceDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory; 
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CounterDAO#findById(long)
	 */
	@Override
	public WkstCounter findById(long id){
		return (WkstCounter) sessionFactory.getCurrentSession().get(WkstCounter.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CounterDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstCounter> findAll(){
		return sessionFactory.getCurrentSession().createQuery("FROM WkstCounter w").list();
	}
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CounterDAO#save(com.vector.model.WkstCounter)
	 */
	@Override
	public void save(WkstCounter counter){
		sessionFactory.getCurrentSession().save(counter);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CounterDAO#delete(long)
	 */
	@Override
	public void delete(long id){
		WkstCounter counter = findById(id);
		
		if(counter != null)
			sessionFactory.getCurrentSession().delete(counter);
	}
}
