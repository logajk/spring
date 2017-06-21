package com.abalia.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.abalia.SispMongoApplication;
import com.abalia.model.Demanda;
import com.abalia.service.DemandaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SispMongoApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class GitHubControllerTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DemandaService service;
	
	@Test
	public void testGetInformation() throws Exception{
		this.mockMvc.perform(post("/github")
				.param("id", "44359759"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", Matchers.comparesEqualTo("logajk")));
			
	}
	
	@Test
	public void testGetInformation2() throws Exception{
		this.mockMvc.perform(post("/github")
				.param("id", "104092"))
			.andExpect(status().isNotFound());
			
	}
	
	@Test
	public void testGetInformation3() throws Exception{
		
		List<Demanda> listado = service.findAll();
		
		for(Demanda d : listado){
			if(!d.getId().equals("44359759") && !d.getId().equals("73687")){
				log.debug("Buscando perfil de: " + d.getId());
				this.mockMvc.perform(post("/github")
						.param("id", d.getId()))
					.andExpect(status().isNotFound());
			}
		}
	}
}
