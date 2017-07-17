package com.abalia.processor;

import org.springframework.batch.item.ItemProcessor;

import com.abalia.model.PersonaDTO;

public class PersonaDTOItemProcessor implements ItemProcessor<PersonaDTO, PersonaDTO> {

	@Override
	public PersonaDTO process(PersonaDTO persona) throws Exception {
		// TODO Auto-generated method stub
		return new PersonaDTO(persona.getNombre().toUpperCase(), persona.getApellidos().toUpperCase(), persona.getFecha());
	}
}
