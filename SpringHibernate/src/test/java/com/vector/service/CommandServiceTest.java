package com.vector.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vector.init.WebAppConfigTest;
import com.vector.model.WkstCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
@Transactional
public class CommandServiceTest {

	static final Logger LOGGER = Logger.getLogger(CommandServiceTest.class);
	
	@Autowired
	private CommandService service;
	
	@Autowired
	private WorkstationService workstationService;
	
	@Test
	public void testFindById() {
		WkstCommand command = service.findById(841);
		
		assertNotNull(command);
		
		LOGGER.info(command);
	}

	@Test
	public void testFindAll() {
		List<WkstCommand> listado = service.findAll();
		
		assertNotNull(listado);
		
		LOGGER.info("Numero de comandos: "+listado.size());
		
		for(WkstCommand command : listado){
			LOGGER.info(command);
		}
	}

	@Test
	public void testFindByIdWithWkst() {
		List<WkstCommand> listado = service.findByIdWithWkst("E005");
		
		assertNotNull(listado);
		
		LOGGER.info("Numero de comandos: "+listado.size());
		
		for(WkstCommand command : listado){
			LOGGER.info(command);
		}
	}
	
	@Test
	public void testSave(){
		WkstCommand command = new WkstCommand();
		
		command.setWorkstation(workstationService.findByID("LATM0008"));
		command.setCommand("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><atm_commands></atm_commands>");
//		command.setCommand("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><atm_commands>");
//		command.setCommand("Prueba 1");
		
		int tamanio = service.findAll().size();
		
		try {
			service.save(command);
			
			assertEquals(tamanio+1, service.findAll().size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
	}
	
	@Test
	public void testDelete(){
		
		List<WkstCommand> listado = service.findAll();
		
		assertNotNull(listado);
		
		int tamanio = listado.size();
		
		service.delete(listado.get(0).getId());
		
		assertEquals(tamanio-1, service.findAll().size());
	}
}
