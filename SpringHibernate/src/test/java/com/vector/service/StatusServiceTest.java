package com.vector.service;

import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vector.init.WebAppConfigTest;
import com.vector.model.WkstStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
public class StatusServiceTest {

	static final Logger LOGGER = Logger.getLogger(StatusServiceTest.class);
	
	@Autowired
	private StatusService service;
	
	@Test
	public void testFindById(){
		WkstStatus status = service.findByID("IN_SERVICE");
		
		LOGGER.debug(status);
		
		assertNotNull(status);
	}
	
	@Test
	public void testAddStatus(){
		WkstStatus status = new WkstStatus();
		
		status.setId("STATUS_TEST2");
		status.setName("STATUS_TEST2");
		
		int numStatus = service.findAll().size();
		
		LOGGER.debug("Guardando estado: "+status);
		
		service.addStatus(status);
		
		assertEquals(numStatus+1, service.findAll().size());
		
		service.deleteStatus("STATUS_TEST2");
	}
	
	@Test
	public void testUpdateStatus(){

		if(service.findByID("STATUS_TEST") == null)
			service.addStatus(new WkstStatus("STATUS_TEST", "STATUS_TEST"));
		
		WkstStatus status = service.findByID("STATUS_TEST");
		
		status.setName("STATUS_TEST1");
		
		service.updateStatus(status);
		
		assertEquals("STATUS_TEST1", service.findByID("STATUS_TEST").getName());
		
//		service.deleteStatus("STATUS_TEST");
	}
	
	@Test
	public void testDeleteStatus(){
		
		if(service.findByID("STATUS_TEST") == null)
			service.addStatus(new WkstStatus("STATUS_TEST", "STATUS_TEST"));
		
		int numStatus = service.findAll().size();
		
		service.deleteStatus("STATUS_TEST");
		
		assertEquals(numStatus-1, service.findAll().size());
		assertNull(service.findByID("STATUS_TEST"));
	}
}
