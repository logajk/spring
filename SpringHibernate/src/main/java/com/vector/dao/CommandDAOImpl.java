package com.vector.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vector.model.WkstCommand;

@Repository
public class CommandDAOImpl implements CommandDAO {

static final Logger LOGGER = Logger.getLogger(CommandDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CommandDAO#findById(int)
	 */
	@Override
	public WkstCommand findById(int id){
		return (WkstCommand) sessionFactory.getCurrentSession().get(WkstCommand.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CommandDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstCommand> findAll(){
		return sessionFactory.getCurrentSession().createQuery("FROM WkstCommand w").list();
	}
	
	/* (non-Javadoc)
	 * @see com.vector.dao.CommandDAO#findByIdWithWkst(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<WkstCommand> findByIdWithWkst(String id){
		return sessionFactory.getCurrentSession().createQuery("FROM WkstCommand w JOIN FETCH w.workstation WHERE w.workstation.wkstid like :id")
				.setString("id", String.format("%1$-8s", id))
				.list();
	}
	
	@Override
	public void save(WkstCommand command){
		sessionFactory.getCurrentSession().save(command);
	}
	
	@Override
	public void delete(int id){
		WkstCommand command = findById(id);
		
		if(command != null)
			sessionFactory.getCurrentSession().delete(command);
	}
}
