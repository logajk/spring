package com.abalia.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abalia.model.demanda.Demanda;
import com.abalia.model.linkedin.Linkedin;
import com.abalia.model.linkedin.Profile;
import com.abalia.service.DemandaService;
import com.abalia.service.LinkedinService;
import com.jaunt.Cookie;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

@RestController
@RequestMapping("/linkedin")
@Api(name="Servicio LinkedIn", description="Metodos para obterner infomacion del portal LinkedIn", visibility=ApiVisibility.PUBLIC, stage=ApiStage.RC)
@ApiVersion(since="1.0")
public class LinkedinController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${url.login.linkedin}")
	private String url_linkedin;
	
	@Value("${usuario.linkedin}")
	private String username_linkedin;
	
	@Value("${contrasenya.linkedin}")
	private String password_linkedin;
	
	@Value("${url.consulta.linkedin}")
	private String url_busqueda;
	
	@Autowired
	private LinkedinService linkedinService;
	
	@Autowired
	private DemandaService demandaService;
	
	
	@RequestMapping("/findById")
	@ApiMethod(description="Devuelve el perfil del demandante en el portal LinkedIn")
	public ResponseEntity<Linkedin> findById(@ApiQueryParam(name="id", description="Id del perfil a buscar") @RequestParam(value="id")String id){
		
		Linkedin linkedin = linkedinService.findById(id);
		
		if(linkedin==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Linkedin>(linkedin, HttpStatus.OK);
		}
	}
	
	@RequestMapping("/findById_demanda")
	@ApiMethod(description="Devuelve el perfil del demandante en el portal LinkedIn")
	public ResponseEntity<Linkedin> findById_demanda(@ApiQueryParam(name="id_demanda", description="Id del demandante")@RequestParam(value="id_demanda") String id_demanda){
		
		log.debug("Parametro recibido: "+ id_demanda);
		
		Linkedin linkedin = linkedinService.findById_demanda(id_demanda);
		
		if(linkedin==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Linkedin>(linkedin, HttpStatus.OK);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ApiMethod(description="Inserta un nuevo perfil del demandante del portal LinkedIn")
	public ResponseEntity<Linkedin> insert(@RequestParam(value="id_demanda")String id_demanda){
		
		Linkedin linkedin = new Linkedin();
		Demanda demanda;
		UserAgent userAgent = getUserAgent();
		List<BasicNameValuePair> paramentros = new ArrayList<>();
		
		
		if(demandaService.exists(id_demanda) && login2(userAgent)){
			log.debug("Cookies: "+ userAgent.cookies.toString());
			
			log.debug(userAgent.doc.innerHTML());
			demanda = demandaService.findById(id_demanda);
			
			log.debug("Voay a buscar el candidato");
				
			try {
				paramentros.add(new BasicNameValuePair("origin", "FACETED_SEARCH"));
				paramentros.add(new BasicNameValuePair("firstName", demanda.getNombre()));
				paramentros.add(new BasicNameValuePair("lastName", demanda.getApellidos()));
				
//				userAgent.sendGET(url_busqueda + "&firstName=" + URLEncoder.encode(StringUtils.stripAccents(demanda.getNombre()), "UTF-8") + "&lastName=" + URLEncoder.encode(StringUtils.stripAccents(demanda.getApellidos()), "UTF-8"));
				
				
				userAgent.visit(url_busqueda + "&firstName=" + URLEncoder.encode(StringUtils.stripAccents(demanda.getNombre()), StandardCharsets.UTF_8.name()) + "&lastName=" + URLEncoder.encode(StringUtils.stripAccents(demanda.getApellidos()), StandardCharsets.UTF_8.name()));
				
				Element e = userAgent.doc.findFirst("<h3 class=search-results__total>");
				
				if(Integer.parseInt(e.getText())!=1){
					//Vamos a visitar el perfil del candidato.
					linkedin = extractLinkedIn(userAgent, userAgent.doc.findFirst("<div class=search-result__info>").findFirst("<a>").getAt("href"));
					if(linkedin!=null){
						return new ResponseEntity<Linkedin>(linkedin, HttpStatus.OK);
					}
				}else{
					log.debug("Se ha encontrado mas de un perfil para: " + demanda.getNombre() + " " + demanda.getApellidos());
				}
				
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
				log.error("Request URL: "+e.getRequestUrlData());
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	private Linkedin extractLinkedIn(final UserAgent userAgent, String link){
		
		Linkedin linkedin = new Linkedin();
		
		try {
			userAgent.visit(link);
			
			linkedin.setId(userAgent.doc.findFirst("<a data-control-name>").getAt("id"));
			Elements elementos;
			Profile profile;
			//Vamos a buscar la experiencia
			try {
				elementos = userAgent.doc.findFirst("<h2>Experiencia").getParent().nextSiblingElement().findEvery("<li>");
				
				for(Element e : elementos){
					profile = new Profile();
					profile.setTitulo(e.findFirst("<div class=pv-entity__summary-info>").findFirst("<h3>").getText().trim());
					profile.setCentro(e.findFirst("<div class=pv-entity__summary-info>").findFirst("<h4>").innerText().trim());
					linkedin.getExperiencia().add(profile);
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.debug("El candidato no tiene experiencia");
			}
			//Vamos a buscar la educacion
			try {
				elementos = userAgent.doc.findFirst("<h2>Educación").getParent().nextSiblingElement().findEvery("<li>");
				
				for(Element e : elementos){
					profile = new Profile();
					profile.setTitulo(e.findFirst("<div class=pv-entity__summary-info>").findFirst("<h3>").getText().trim());
					profile.setCentro(e.findFirst("<div class=pv-entity__summary-info>").findFirst("<h4>").innerText().trim());
					linkedin.getEducacion().add(profile);
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.debug("El candidato no tiene educacion");
			}
			//Vamos a buscar Aptitudes
			try {
				elementos = userAgent.doc.findFirst("<h2>Aptitudes").getParent().nextSiblingElement().findEvery("<span class=pv-skill-entity__skill-name>");
				log.debug("Se han localidado "+elementos.size() + " aptitudes");
				for(Element e : elementos){
					
					linkedin.getAptitudes().add(e.getText().trim());
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.debug("El candidato no tiene educacion");
			}
			
			return linkedin;
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
			log.error("Request URL: "+e.getRequestUrlData());
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param userAgent
	 * @return
	 */
	private boolean login(UserAgent userAgent){
		
		try {
			userAgent.visit(url_linkedin);
//			log.debug(userAgent.doc.innerHTML());
			userAgent.doc.getForm("<form action=https://www.linkedin.com/uas/login-submit>").setTextField("session_key", username_linkedin);
			userAgent.doc.getForm("<form action=https://www.linkedin.com/uas/login-submit>").setPassword("session_password", password_linkedin);
			userAgent.doc.getForm("<form action=https://www.linkedin.com/uas/login-submit>").submit();
			log.debug("Me he logado correctamente");
			return true;
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
			log.error("Request URL: "+e.getRequestUrlData());
			if(e.getStatusCode()==403){
				log.debug(userAgent.doc.innerHTML());
			}
			
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		log.debug("No me he podido logear en el portal");
		return false;
	}
	
	private boolean login2(UserAgent userAgent){
		
		List<BasicNameValuePair> paramentros = new ArrayList<>();
		
		try {
			userAgent.visit(url_linkedin);
			log.debug(userAgent.cookies.toString());
			paramentros.add(new BasicNameValuePair("session_key", username_linkedin));
			paramentros.add(new BasicNameValuePair("session_password", password_linkedin));
			paramentros.add(new BasicNameValuePair("isJsEnabled", "false"));
			
			for(Cookie c : userAgent.cookies.getCookies()){
				if(c.getName().equals("bcookie")){
					log.debug(c.getValue().replaceAll("\"", "").split("&")[1]);
					paramentros.add(new BasicNameValuePair("loginCsrfParam", c.getValue().replaceAll("\"", "").split("&")[1]));
				}
			}
			log.debug(paramentros.toString());
			userAgent.sendPOST("https://www.linkedin.com/uas/login-submit", URLEncodedUtils.format(paramentros, StandardCharsets.UTF_8));
			return true;
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
			log.error("Request URL: "+e.getRequestUrlData());
			log.debug(userAgent.doc.innerHTML());
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	private UserAgent getUserAgent(){
		
		UserAgent userAgent = new UserAgent();
		Map<String, String> header = new HashMap<>();
		
//		header.put("accept", "*/*");
		header.put("accept-language", "es,en;q=0.8");
//		header.put("accept-encoding", "gzip");
//		header.put("connection", "Keep-Alive");
//		header.put("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


		userAgent.settings.autoSaveAsHTML = true;
		userAgent.settings.charset = "iso-8859-1";
		userAgent.settings.defaultRequestHeaders=header;

		return userAgent;
	}
}