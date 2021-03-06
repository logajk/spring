package com.vector.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vector.init.WebAppConfigTest;
import com.vector.model.WkstCounter;
import com.vector.model.WkstWorkstation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
@Transactional
public class CounterServiceTest {

	static final Logger LOGGER = Logger.getLogger(CounterServiceTest.class);
	
	@Autowired
	private CounterService counterService;
	
	@Autowired 
	private WorkstationService workstationService;
	
	@Test
	public void testFindById() {
		WkstCounter counter = counterService.findById(893);
		
		assertNotNull(counter);
	}

	@Test
	public void testFindAll() {
		List<WkstCounter> contadores = counterService.findAll();
		
		assertNotNull(contadores);
		
		assertTrue(contadores.size() > 0);
	}

	@Test
	public void testSave() {
		WkstWorkstation wkst = workstationService.findByIdWithCounters("LATM0006");
		
//		WkstWorkstation wkst = workstationService.findByID("A444001A");
		
		assertNotNull(wkst);
		
		WkstCounter contador = new WkstCounter();
		contador.setName("Contador prueba 2");
		contador.setValue(22.2);
		contador.setWorkstation(wkst);
//		wkst.getCounters().add(contador);
		
		counterService.save(contador);
		
		assertNotNull(contador.getId());
	}
	
	@Test
	@Ignore
	public void testDeleteAllCounters(){
		WkstWorkstation wkst = workstationService.findByIdWithCounters("LATM0006");
		
		assertNotNull(wkst);
		
		int tamanio = wkst.getCounters().size();
		
		counterService.delete(wkst.getCounters().get(0).getId());
		
//		for(WkstCounter counter : wkst.getCounters()){
//			counterService.delete(counter.getId());
//		}
		
		assertEquals(tamanio-1, wkst.getCounters().size());
		
//		assertTrue(wkst.getCounters().isEmpty());
	}
}
