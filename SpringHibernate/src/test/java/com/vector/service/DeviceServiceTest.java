package com.vector.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vector.init.WebAppConfigTest;
import com.vector.model.Status;
import com.vector.model.Type;
import com.vector.model.WkstDevice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
public class DeviceServiceTest {

	static final Logger LOGGER = Logger.getLogger(DeviceServiceTest.class);
	
	@Autowired
	private DeviceService service;
	
	@Autowired
	private WorkstationService workstationService;
	
	@Test
	public void testFindById() {
		WkstDevice device = service.findById(792599);
		
		assertNotNull(device);
		
		LOGGER.info(device);
	}

	@Test
	public void testFindAll() {
		List<WkstDevice> listado = service.findAll();
		
		assertNotNull(listado);
		
		for(WkstDevice device : listado){
			LOGGER.info(device);
		}
	}
	
	@Test
	public void testAddDevice(){
		WkstDevice device = new WkstDevice();
		
		device.setLogicalname("CASHIN_CASSETTE_TEST");
		device.setStatusId(Status.DISABLED);
		device.setTypeId(Type.CASHIN_CASSETTE);
		device.setWorkStation(workstationService.findByID("A444001A"));
		
		service.addDevice(device);
		
		assertNotNull(device.getId());
	}
	
	@Test
	public void testRemoveDevice(){
		List<WkstDevice> listado = service.findByLogicalName("%TEST");
		
		assertNotNull(listado);
		assertTrue(listado.size() > 0);
		
		for(WkstDevice device : listado){
			service.delete(device.getId());
		}
	}
}
