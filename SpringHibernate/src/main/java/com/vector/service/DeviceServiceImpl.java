package com.vector.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vector.dao.DeviceDAO;
import com.vector.model.WkstDevice;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	static final Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private DeviceDAO deviceDAO;
	
	/* (non-Javadoc)
	 * @see com.vector.service.DeviceService#findById(long)
	 */
	@Override
	public WkstDevice findById(long id){
		return deviceDAO.findById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.service.DeviceService#findAll()
	 */
	@Override
	public List<WkstDevice> findAll(){
		return deviceDAO.findAll();
	}
	
	@Override
	public void addDevice(WkstDevice device){
		deviceDAO.addDevice(device);
	}
	
	@Override
	public List<WkstDevice> findByLogicalName (String logicalName){
		return deviceDAO.findByLogicalName(logicalName);
	}
	
	@Override
	public void delete(long id){
		deviceDAO.delete(id);
	}
}
