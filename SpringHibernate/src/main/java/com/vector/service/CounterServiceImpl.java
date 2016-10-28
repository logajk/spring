package com.vector.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.dao.CounterDAO;
import com.vector.model.WkstCounter;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {

	@Autowired
	private CounterDAO counterDAO;
	
	/* (non-Javadoc)
	 * @see com.vector.service.CounterService#findById(long)
	 */
	@Override
	public WkstCounter findById(long id){
		return counterDAO.findById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.service.CounterService#findAll()
	 */
	@Override
	public List<WkstCounter> findAll(){
		return counterDAO.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.vector.service.CounterService#save(com.vector.model.WkstCounter)
	 */
	@Override
	public void save(WkstCounter counter){
		counterDAO.save(counter);
	}
	
	@Override
	public void delete(long id){
		counterDAO.delete(id);
	}
}
