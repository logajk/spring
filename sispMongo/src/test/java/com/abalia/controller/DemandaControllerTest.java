package com.abalia.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.abalia.SispMongoApplication;
import com.abalia.model.Portal;
import com.abalia.model.demanda.Demanda;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SispMongoApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class DemandaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getById() throws Exception{
		this.mockMvc.perform(get("/demanda/getById").param("id", "106741"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.email", Matchers.comparesEqualTo("anxo.wl@gmail.com")));
	}
	
	@Test
	public void getAll() throws Exception{
		this.mockMvc.perform(get("/demanda/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
	
	@Test
	@Ignore
	public void insert() throws Exception{
		
		Demanda demanda = new Demanda();
		
		demanda.getConocimientos().add("java");
		demanda.getConocimientos().add("spring");
		demanda.setEmail("prueba@prueba.com");
		demanda.setFechaAlta(new Date());
		demanda.setId("123456789");
		demanda.setNombre("prueba");
		demanda.setPortal(Portal.MONSTER);
		demanda.setTitulo("prueba");
		
		Gson gson = new Gson();
		
		this.mockMvc.perform(post("/demanda/insert")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(gson.toJson(demanda)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void getPerfiles() throws Exception{
		this.mockMvc.perform(get("/demanda/getPerfiles"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
	
	@Test
	public void getHistorico() throws Exception{
		this.mockMvc.perform(get("/demanda/getHistorico")
				.param("titulo", "Ingeniero Superior"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.['1 semana']", Matchers.comparesEqualTo(11)));
	}
	
	@Test
	public void updateFechaAlta() throws Exception{
		this.mockMvc.perform(put("/demanda/updateFechaAlta")
				.param("id", "44359759")
				.param("fecha", "20062017_123045"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.fechaAlta", Matchers.comparesEqualTo("20/06/2017 10:30:45")));
	}
	
	@Test
	public void getConocimientosByDemanda() throws Exception{
		this.mockMvc.perform(get("/demanda/getConocimientosByDemanda")
				.param("titulo", "Ingeniero Superior"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.comparesEqualTo(84)));
	}
	
	@Test
	public void getFrecuenciaConocimientosByDemanda() throws Exception{
		this.mockMvc.perform(get("/demanda/getFrecuenciaConocimientosByDemanda")
				.param("titulo", "Ingeniero Superior"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.java", Matchers.comparesEqualTo(11)));
	}
	
	@Test
	public void getOfertas() throws Exception{
		this.mockMvc.perform(get("/demanda/getOfertas")
				.param("id", "106741")
				.param("notaCorte", "80"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
	}
}
