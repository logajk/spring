package com.abalia.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.abalia.SispMongoApplication;
import com.abalia.model.Portal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SispMongoApplication.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebAppConfiguration
@AutoConfigureMockMvc
public class OfertaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void getPerfiles() throws Exception {
		this.mockMvc.perform(get("/oferta/getPerfiles").param("portal", Portal.MONSTER.toString())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("perfiles"));
	}
	
	@Test
	public void getOfertas() throws Exception {
		this.mockMvc.perform(get("/oferta/getOfertas").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("oferta"));
	}
}
