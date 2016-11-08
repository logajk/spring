package com.vector.service;

import org.apache.log4j.Logger;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vector.init.WebAppConfigTest;
import com.vector.model.WkstGroup;
import com.vector.model.WkstWorkstation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
@Transactional
public class GroupServiceTest {

	static final Logger LOGGER = Logger.getLogger(GroupServiceTest.class);
	
	@Autowired
	private GroupService service;
	
	@Test
	public void testFindById(){
		WkstGroup grupo = service.findById(5);
		
		assertNotNull(grupo);
		
		LOGGER.debug(grupo);
		
		assertNotNull(grupo.getWorkstations());
		
		for(WkstWorkstation w : grupo.getWorkstations()){
			LOGGER.debug(w);
		}
	}
}
