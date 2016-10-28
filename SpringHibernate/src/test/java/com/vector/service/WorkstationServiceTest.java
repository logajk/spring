package com.vector.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.vector.model.WkstCommand;
import com.vector.model.WkstCounter;
import com.vector.model.WkstDevice;
import com.vector.model.WkstStatus;
import com.vector.model.WkstStatusreason;
import com.vector.model.WkstWorkstation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebAppConfigTest.class)
public class WorkstationServiceTest {

	static final Logger LOGGER = Logger.getLogger(WorkstationServiceTest.class);
	
	@Autowired
	private WorkstationService service;
	
	@Test
	public void testFindByID(){
		WkstWorkstation wkst = service.findByID("A444001A");
		
		assertNotNull(wkst);
		
		LOGGER.debug(wkst);
		
		LOGGER.debug(wkst.getStatusId());
		
	}
	
	@Test
	public void testFindByIdWithDevices(){
		WkstWorkstation wkst = service.findByIdWithDevices("A444001A");
		
		assertNotNull(wkst);
		
		LOGGER.debug("Se han recuperado " + wkst.getDevices().size() + " dispositivos");
		
		for(WkstDevice device : wkst.getDevices()){
			LOGGER.debug(device);
		}
	}
	
	@Test
	public void testFindByIdWithStatusReason(){
		WkstWorkstation workstation = service.findByIdWithStatusReason("LATM0008");
		
		assertNotNull(workstation);
		
		LOGGER.debug(workstation.getStatusreasonId());
	}
	
	@Test
	public void testFindByIdWithCounters(){
		WkstWorkstation workstation = service.findByIdWithCounters("A444001A");
		
		assertNotNull(workstation);
		
		LOGGER.info("Número de contadores recibidos son: " + workstation.getCounters().size());
		
		for(WkstCounter c : workstation.getCounters()){
			LOGGER.info(c);
		}
	}
	
	@Test
	public void testGetAllWithDevices(){
		 List<WkstWorkstation> listado = service.getAllWithDevices();
		
		assertNotNull(listado);
		
		WkstWorkstation workstation = listado.get(0);
		
		LOGGER.debug("Numero de dispositivos: "+workstation.getDevices().size());
		
		for(WkstDevice device : workstation.getDevices()){
			LOGGER.debug(device);
		}
	}
	
	@Test
	public void testAddWkst(){
		WkstWorkstation wkst = populateATM("ATMTEST");
		
		int tamOriginal = service.getAll().size();
		
		service.addWkst(wkst);
		
		assertEquals(tamOriginal+1, service.getAll().size());
	}
	
	@Test
	public void testUpdateWkst(){
		WkstWorkstation wkst = service.findByID("LATM0008");
		
		wkst.setCountry("ES");
		
		service.updateWkst(wkst);
		
		assertEquals("ES", service.findByID("LATM0008").getCountry());
	}
	
	@Test
	public void testUpdateDevices(){
		WkstWorkstation wkst = service.findByIdWithDevices("ATM_TEST");
		
		assertNotNull(wkst);
		
		wkst.setDevices(populateDevice());
		
		service.updateWkst(wkst);
		
		assertTrue(service.findByIdWithDevices("LATM0008").getDevices().size() > 0);
	}
	
	@Test
	public void testDeleteWkst(){
		WkstWorkstation wkst = service.findByID("ATMTEST");
		
		LOGGER.debug(wkst);
		
		int tamOriginal = service.getAll().size();
		
		service.deleteWkst(wkst.getWkstid());
		
		assertEquals(tamOriginal-1, service.getAll().size());
	}
	
	private WkstWorkstation populateATM(String id){
		WkstWorkstation wkst = new WkstWorkstation();
		
		wkst.setWkstid(id);
		wkst.setAddress("Cerro de los Gamos");
		wkst.setBranch("1");
		wkst.setBranchcode("sucu");
		wkst.setCity("Pozuelo");
		wkst.setCountry("ES");
		wkst.setCounty("");
		wkst.setDescription("Cajero de pruebas");
		wkst.setInstallationdate(new Date());
		wkst.setIpaddress("");
		wkst.setIpport(8080);
		wkst.setLasttouched(new Timestamp(new Date().getTime()));
		wkst.setLastupdate(new Timestamp(new Date().getTime()));
		wkst.setNumsecop(300);
		wkst.setPrevstatusId(Status.IN_SERVICE);
		wkst.setPushmode("0");
		wkst.setRechargercode("");
		wkst.setRemark("");
		wkst.setSecurepercentage(new BigDecimal("100.00"));
		wkst.setSerialnumber("");
		wkst.setSettlementdate(new Timestamp(new Date().getTime()));
		wkst.setSocialdeprivation("0");
		wkst.setState("ES");
		wkst.setStatusId(new WkstStatus("OFFLINE", "OFFLINE"));
		wkst.setStatusreasonId(new WkstStatusreason(1, "By command", "COMMAND"));
		wkst.setTimestamp(new Date().getTime());
		wkst.setTimezone("GMT+01:00");
		wkst.setTmkdate(new Date());
		wkst.setTpkcount(3538);
		wkst.setTpkdate(new Date());
		wkst.setTrncount(381);
		wkst.setVendorid("WINCOR");
		wkst.setZipcode("28000");
		
		wkst.setDevices(populateDevice());
		
		wkst.setCounters(populateCounter());
		
		wkst.setCommands(populateCommand());
		
		for(WkstDevice device : wkst.getDevices())
			device.setWorkStation(wkst);
		
		
		for(WkstCounter counter : wkst.getCounters())
			counter.setWorkstation(wkst);
		
		for(WkstCommand command : wkst.getCommands())
			command.setWorkstation(wkst);
		
		return wkst;
	}
	
	private List<WkstDevice> populateDevice(){
		WkstDevice device = new WkstDevice();
		List<WkstDevice> listado = new ArrayList<WkstDevice>();
		
		device.setLogicalname("CASHIN_CASSETTE_TEST1");
		device.setStatusId(Status.DISABLED);
		device.setTypeId(Type.CASHIN_CASSETTE);
		
		listado.add(device);
		
		device = new WkstDevice();
		device.setLogicalname("CASHIN_CASSETTE_TEST2");
		device.setStatusId(Status.DISABLED);
		device.setTypeId(Type.CASHIN);
		
		listado.add(device);
		
		return listado;
	}
	
	private List<WkstCounter> populateCounter(){
		WkstCounter counter = new WkstCounter();
		List<WkstCounter> listado = new ArrayList<WkstCounter>();
		
		counter.setName("DEPOSIT_500_GBP_Dispensed");
		counter.setValue(11.1);
		
		listado.add(counter);
		
		counter = new WkstCounter();
		
		counter.setName("DEPOSIT_500_USD_Dispensed");
		counter.setValue(22.2);
		
		listado.add(counter);
		
		return listado;
	}
	
	private List<WkstCommand> populateCommand(){
		WkstCommand command = new WkstCommand();
		List<WkstCommand> listado = new ArrayList<WkstCommand>();
		
		command.setCommand("Comando_1");
		
		listado.add(command);
		
		command = new WkstCommand();
		
		command.setCommand("Comando_2");
		
		listado.add(command);
		
		return listado;
	}
}
