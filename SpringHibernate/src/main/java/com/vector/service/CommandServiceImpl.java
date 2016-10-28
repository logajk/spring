package com.vector.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.vector.dao.CommandDAO;
import com.vector.model.WkstCommand;

@Service
@Transactional
public class CommandServiceImpl implements CommandService {

	static final Logger LOGGER = Logger.getLogger(CommandServiceImpl.class);
	
	@Autowired
	private CommandDAO commandDAO;
	
	/* (non-Javadoc)
	 * @see com.vector.service.CommandService#findById(int)
	 */
	@Override
	public WkstCommand findById(int id){
		return commandDAO.findById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vector.service.CommandService#findAll()
	 */
	@Override
	public List<WkstCommand> findAll(){
		return commandDAO.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.vector.service.CommandService#findByIdWithWkst(java.lang.String)
	 */
	@Override
	public List<WkstCommand> findByIdWithWkst(String id){
		return commandDAO.findByIdWithWkst(id);
	}
	
	@Override
	public void save(WkstCommand command) throws Exception{
		
		if(isValidXML(command.getCommand()))
				commandDAO.save(command);
		else
			throw new Exception("XML not valid");
	}
	
	@Override
	public void delete(int id){
		
		commandDAO.delete(id);
	}
	
	@SuppressWarnings("unused")
	private boolean isValidXML(String xml){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document =  builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
			LOGGER.info("XML formado correctamente");
			return true;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			return false;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			return false;
		}
	}
}
