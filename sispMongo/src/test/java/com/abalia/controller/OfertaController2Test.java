package com.abalia.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.abalia.SispMongoApplication;
import com.abalia.model.Portal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SispMongoApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class OfertaController2Test {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getPerfiles() throws Exception{
		this.mockMvc.perform(get("/oferta/getPerfiles").param("portal", Portal.MONSTER.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getHistorico() throws Exception{
		this.mockMvc.perform(get("/oferta/getHistorico").param("titulo", "Programadores Java")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.['1 dia']", Matchers.comparesEqualTo(0)));
	}
	
	@Test
	public void getOfertas() throws Exception{
		this.mockMvc.perform(get("/oferta/getOfertas")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
	
	@Test
	public void getRequisitosByOferta() throws Exception{
		this.mockMvc.perform(get("/oferta/getRequisitosByOferta").param("titulo", "Programadores Java")
			.accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
	
	@Test
	public void getFrecuenciaRequisitosByOferta() throws Exception{
		this.mockMvc.perform(get("/oferta/getFrecuenciaRequisitosByOferta").param("titulo", "Programadores Java"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.com", Matchers.comparesEqualTo(2)));
	}
	
	@Test
	public void getByRequisitos() throws Exception{
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("requisitos", "java");
		param.add("requisitos", "com");
		
		this.mockMvc.perform(get("/oferta/getByRequisitos").params(param))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
	
	@Test
	public void getCandidatos() throws Exception{
		this.mockMvc.perform(get("/oferta/getCandidatos").param("id", "10161"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
}
