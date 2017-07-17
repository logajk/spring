package com.abalia.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.abalia.model.Persona;

public class PersonaItemProcessor implements ItemProcessor<Persona, Persona> {

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Override
	public Persona process(Persona persona) throws Exception {
		// TODO Auto-generated method stub
		final String nombre = persona.getNombre().toUpperCase();
		final String apellidos = persona.getApellidos().toUpperCase();
		
		final Persona transformedPersona = new Persona(nombre, apellidos);
		
		log.debug("Convirtiendo (" + persona + ") en ("+transformedPersona +")");
		
		return transformedPersona;
	}
}
