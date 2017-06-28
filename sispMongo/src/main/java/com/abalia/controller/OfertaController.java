package com.abalia.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;
import com.abalia.model.demanda.DemandaExt;
import com.abalia.model.oferta.Oferta;
import com.abalia.model.tag.Tag;
import com.abalia.service.OfertaService;
import com.abalia.service.TagService;
import com.abalia.utils.ComunCollection;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;


@RestController
@RequestMapping("/oferta")
@Api(name="Servicio de ofertas", description="Metodos para gestionar las ofertas", visibility=ApiVisibility.PUBLIC, stage=ApiStage.RC)
@ApiVersion(since="1.0")
public class OfertaController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String STEFANINI = "Stefanini";
	
	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private TagService tagService;
	
	@Value("${url.login.monster}")
	private String url_monster;
	
	@Value("${url.login.infojobs}")
	private String url_infojobs;
	
	@Value("${usuario.infojobs}")
	private String usernameInfojobs;
	@Value("${contrasenya.infojobs}")
	private String passwordInfojobs;
	
	@Value("${url.ofeta.miltrabajos}")
	private String url_mil_trabajos;
	
	
	@RequestMapping("/getOfertas")
	@ApiMethod(description="Obtiene todas las ofertas disponibles", produces= {MediaType.APPLICATION_JSON_VALUE})
	public List<Oferta> getOfertas(){
		
		log.info("Obteniendo ofertas de Monster");
		return ofertaService.findAll();
	}
	
	@RequestMapping("/insertOfertasMonster")
	@ApiMethod(description="Inserta nuevas ofertas de empleo desde el portal MONSTER")
	public List<Oferta> insertOfertasMonster(){
		
		UserAgent userAgent = getUserAgent(Portal.MONSTER);
		List<Oferta> listado = new ArrayList<Oferta>();
		Oferta oferta;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int i=1;
		
		try {
			userAgent.visit(url_monster);
			
			log.info(userAgent.settings.toString());
			
			do {
				Element offerBlock = userAgent.doc.findFirst("<section id=resultsWrapper>");
				if (offerBlock != null) {
					log.debug("Lista encontrada");
				}
				Elements offerList = offerBlock.findEach("<article class=js_result_row>");
				if (offerList != null) {

					log.debug("Ofertas encontradas: " + offerList.size());

					for (Element e : offerList) {
						oferta = new Oferta(
								e.findFirst("<div class=jobTitle>").findFirst("<a>").getAt("data-m_impr_j_jobid"),
								e.findFirst("<div class=jobTitle>").findFirst("<a>").getAt("title"),
								sdf.parse(e.findFirst("<div class=extras>").findFirst("<div class=postedDate>").findFirst("<time>").getAt("datetime").replaceAll("T", " ")), Portal.MONSTER);
						oferta.setRequisitos(getRequisitosFromMonster(userAgent, e.findFirst("<div class=jobTitle>").findFirst("<a>").getAt("href"), e.findFirst("<div class=company>").findFirst("<a>").findFirst("<span>").getText()));
						ofertaService.insertOferta(oferta);
						listado.add(oferta);
					}
				} 
				log.debug("Estoy en la pagina: "+i);
				i++;
				
				userAgent.visit(url_monster+"&page="+i);
			} while(true);
			
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.error(e.getMessage());
			log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
			log.error("Request URL: "+e.getRequestUrlData());
		} catch (NotFound e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.error(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			log.error("Error parsing Date");
		}
		return listado;
	}
	
	@RequestMapping("/insertOfertasMilTrabajos")
	@ApiMethod(description="Inserta nuevas ofertas de empleo desde el portal MIL TRABAJOS")
	public List<Oferta> insertOfertasMilTrabajos(){
		
		UserAgent userAgent = getUserAgent(Portal.MIL_TRABAJOS);
		List<Oferta> listado = new ArrayList<Oferta>();
		Oferta oferta;
		Elements offerList;
		int i=1;
		
		List<BasicNameValuePair> paramentros = new ArrayList<>();
		
		paramentros.add(new BasicNameValuePair("sel_areas", ""));
		paramentros.add(new BasicNameValuePair("sel_subareas", "0"));
		paramentros.add(new BasicNameValuePair("sel_prov", "28"));
		paramentros.add(new BasicNameValuePair("palabra", "java"));
		paramentros.add(new BasicNameValuePair("paso", "si"));
		
		try {
			userAgent.sendPOST(url_mil_trabajos, URLEncodedUtils.format(paramentros, "Cp1252"));
			
			do{
				offerList = userAgent.doc.findEvery("<img src=http://www.miltrabajos.com/img/ficha.gif>");
				
				if(offerList!=null && offerList.size()!=0){
					log.debug("Ofertas encontradas: "+offerList.size());
					
					for(Element e : offerList){
//						log.debug("Link oferta: "+e.getParent().getAt("href"));
						oferta = extractOfertaFromMilTrabajos(userAgent, e.getParent().getAt("href"));
						if(oferta!=null){
//							log.debug(oferta.toString());
							ofertaService.insertOferta(oferta);
							listado.add(oferta);
						}
					}
				}
				
				log.debug("Estoy en la pagina: "+i);
				userAgent.sendPOST(url_mil_trabajos+"?pag="+i++, URLEncodedUtils.format(paramentros, "Cp1252"));
			}while(offerList.size() > 0);
			
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error("ResponseException");
			log.error(e.getMessage());
			log.error("HTTP ERROR CODE: " + new Integer(e.getStatusCode()).toString());
			log.error("############");
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error(e.getMessage());
			log.error("############");
		}
		log.debug("Hemos encontrado " + listado.size() + " ofertas");
		return listado;
	}
	
	/**
	 * M&eacute;todo encargo de devolver un objeto Oferta
	 * @param userAgent 
	 * @param link
	 * @return 
	 */
	private Oferta extractOfertaFromMilTrabajos(final UserAgent userAgent, String link){
		
		Oferta oferta = new Oferta();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			userAgent.visit(link);
			
			if (!ofertaService.exists(userAgent.doc.findFirst("<input name=cod_o>").getAt("value"))) {
				oferta.setId(userAgent.doc.findFirst("<input name=cod_o>").getAt("value"));
				oferta.setTitulo(userAgent.doc.findFirst("<td>Cargo").nextSiblingElement().getText().trim());
				oferta.setPortal(Portal.MIL_TRABAJOS);
				oferta.setFechaAlta(sdf.parse(userAgent.doc.findFirst("<input name=txtFecha>").getAt("value")));
				
				String requisitos = userAgent.doc.findFirst("<form name=frm_inscripcion>").innerText();
				
				for(Tag t : tagService.findAll()){
					if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(requisitos, t.getTagName())){
						oferta.getRequisitos().add(t.getTagName());
					}
				}
				return oferta;
			}
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error("ResponseException");
			log.error(e.getMessage());
			log.error("HTTP ERROR CODE: " + new Integer(e.getStatusCode()).toString());
			log.error("############");
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error(e.getMessage());
			log.error("############");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("Error al transformar una flecha");
			log.error(e.getMessage());
		}		
		return null;
	}
	
	private List<String> getRequisitosFromMonster(final UserAgent userAgent, String link, String empresa) {
		
		List<String> tags = new ArrayList<>();
		
		
		log.debug("Buscando requisitos para la empresa: " +empresa);
		log.debug("Link: " + link);
		try {
			userAgent.visit(link);
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.debug("No se ha podido visitar el link de los requisitos de la oferta");
			return tags;
		}
		
		List<Tag> listadoTags = tagService.findAll();
		String requisitos;
		
		if(!empresa.equals(STEFANINI) ){
			try {
				requisitos = userAgent.doc.findFirst("<div id=JobViewContent>").findFirst("<div class=col-md-8>").innerText();
				for(Tag t : listadoTags){
					if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(requisitos, t.getTagName())){
						tags.add(t.getTagName());
					}
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.debug("No se ha podido encontrar el bloque de requisitos para la empresa: "+empresa);
			}
		}else{
			try {
				requisitos = userAgent.doc.findFirst("<div id=CJT_jobBodyContent>").innerText();
				for(Tag t : listadoTags){
					if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(requisitos, t.getTagName())){
						tags.add(t.getTagName());
					}
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.debug("No se ha podido encontrar el bloque de requisitos para la empresa: "+empresa);
//				log.debug("Documento recuperado: "+userAgent.doc.innerText());
			}
		}
		
		return tags;
	}
	
	@RequestMapping("/insertOfertasInfojobs")
	@ApiMethod(description="Inserta nuevas ofertas de empleo desde el portal INFOJOSBS")
	public List<Oferta> insertOfertasInfojobs(){
	
		UserAgent userAgent = getUserAgent(Portal.INFOJOBS);
		List<Oferta> listado = new ArrayList<>();
		Map<String, String> header = new HashMap<>();
		
		try {
//			header.put("accept", "*/*");
//			header.put("accept-language", "es,en;q=0.8");
//			header.put("accept-encoding", "gzip");
//			header.put("connection", "Keep-Alive");
//			header.put("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//			
//			userAgent.settings.defaultRequestHeaders = header;
			
//			userAgent.visit(url_infojobs);
//			
//			Form form = userAgent.doc.getForm(0);
//			form.setTextField("email", username);
//			form.setPassword("password", password);
//			form.submit();
			
			if(logginInfojobs(userAgent)){
				Element offerBlock = userAgent.doc.findFirst("<ul id=offerlist>");
				
				if(offerBlock!=null){
					log.debug("Lista encontrada");
				}
			}
			
			
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error(e.getMessage());
			log.error("############");
		}
		return listado;
	}
	
	
	@RequestMapping("/getPerfiles")
	@ApiMethod(description="Obtiene todos los perfiles ordenados por número de ocurrencias")
	public List<PerfilesCount> getPerfiles(@ApiQueryParam(name="portal", description="Portal desde donde se va a obtener los perfiles", allowedvalues={"MONSTER","INFOJOBS","MIL_TRABAJOS"}) @RequestParam(value="portal") String portal){
		
		List<PerfilesCount> listado = new ArrayList<>();
		
		if(!StringUtils.isEmpty(portal)){
			return ofertaService.getPerfiles(Portal.valueOf(portal.toUpperCase()));
		}
		
		return listado;
	}
	
	
	
	/**
	@RequestMapping("/updateRequisitos")
	@ApiMethod(description="Este método está desactualizado. (NO USAR)")
	@Deprecated
	public List<Oferta> updateRequisitos(){
		
		List<Oferta> listado = ofertaService.findAll();
		
		for(Oferta o : listado){
			if(o.getTitulo().contains("Java") || o.getTitulo().contains("JAVA")){
//				requisito = new Requisito("Java", "Java");
//				requisito.getOferta_id().add(o);				
//				requisitoService.insert(requisito);
				o.getRequisitos().add("Java");
			}
			if(o.getTitulo().contains("J2EE")){
//				requisito = new Requisito("J2EE", "J2EE");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("J2EE");
			}
			if(o.getTitulo().contains("SQL")){
//				requisito = new Requisito("SQL", "SQL");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("SQL");
			}
			if(o.getTitulo().contains("Angular")){
//				requisito = new Requisito("Angular", "Angular");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("Angular");
			}
			if(o.getTitulo().contains("JIRA")){
//				requisito = new Requisito("JIRA", "JIRA");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("JIRA");
			}
			if(o.getTitulo().contains("Full Stack") || o.getTitulo().contains("Fullstack")){
//				requisito = new Requisito("Full Stack", "Full Stack");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("Full Stack");
			}
			if(o.getTitulo().contains("SPRING")){
//				requisito = new Requisito("SPRING", "SPRING");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("SPRING");
			}
			if(o.getTitulo().contains("HIBERNATE")){
//				requisito = new Requisito("HIBERNATE", "HIBERNATE");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("HIBERNATE");
			}
			if(o.getTitulo().contains("UML")){
//				requisito = new Requisito("UML", "UML");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("UML");
			}
			if(o.getTitulo().contains("XML")){
//				requisito = new Requisito("XML", "XML");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("XML");
			}
			if(o.getTitulo().contains("SPRING BATACH")){
//				requisito = new Requisito("SPRING BATACH", "SPRING BATACH");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("SPRING BATACH");
			}
			if(o.getTitulo().contains("Front-end")){
//				requisito = new Requisito("Front-end", "Front-end");
//				requisito.getOferta_id().add(o);
//				requisitoService.insert(requisito);
				o.getRequisitos().add("Front-end");
			}
			ofertaService.updateOferta(o);
		}
		
		return ofertaService.findAll();
	}
	**/
	
	@RequestMapping("/getRequisitosByOferta")
	@ApiMethod(description="Devuelve todos los requitos para una oferta dada")
	public List<String> getRequisitosByOferta(@ApiQueryParam(name="titulo", description="Titulo del perfil a buscar") @RequestParam(value="titulo") String titulo){
		
		List<String> listado = new ArrayList<>();
		
		if(!StringUtils.isEmpty(titulo)){
			listado = ofertaService.getRequisitosByOferta(titulo);
		}
		return listado;
	}
	
	@RequestMapping("/getFrecuenciaRequisitosByOferta")
	@ApiMethod(description="Devuelve todos los requitos para una oferta dada ordenada por ocurrencia de requisitos")
	public Map<String, Long> getFrecuenciaRequisitosByOferta(@ApiQueryParam(name="titulo", description="Titulo del perfil a buscar") @RequestParam(value="titulo") String titulo){
		HashMap<String, Long> mapa = new HashMap<>();
		
		List<String> listado = new ArrayList<>();
		
		if(!StringUtils.isEmpty(titulo)){
			listado = ofertaService.getRequisitosByOferta(titulo);
		}
		
		for(String s : listado){
			mapa.put(s, ofertaService.contarRequisitos(titulo, s));
		}
		
		return ComunCollection.sortMapByValue(mapa);
	}
	
	@RequestMapping("/getHistorico")
	@ApiMethod(description="Devuelve el histórico de un perfil")
	public Map<String, Long> getHistorico(@ApiQueryParam(name="titulo", description="Titulo del perfil a buscar") @RequestParam(value="titulo") String titulo){
		
		return ofertaService.historico(titulo);
	}
	
	@RequestMapping("/getByRequisitos")
	@ApiMethod(description="Devuelve todas las ofertas que cumplan los requisitos que se pasen por par&aacute;metro")
	public List<Oferta> getByRequisitos(@ApiQueryParam(name="requisitos", description="Listado de requisitos") @RequestParam List<String> requisitos){
		
		List<Oferta> listado = ofertaService.findByRequisitos(requisitos);
		
		for(Oferta o : listado){
			log.debug("Se cumple el " + (o.getRequisitos().size() * 100) / requisitos.size()   + "% de los requisitos");
		}
		
		return listado;
	}
	
	@RequestMapping("/getCandidatos")
	@ApiMethod(description="Devuelve todos los candidatos posibles ordenados por coincidencia en los requisitos")
	public List<DemandaExt> getCandidatos(@ApiQueryParam(name="id", description="ID de la oferta a buscar candidatos") @RequestParam(value="id") String id){
		
		return ofertaService.findCandidato(id);
	}
	
	/**
	 * M&eacute;todo encargado de logearse en el portal INFOJOBS
	 * @param userAgent
	 * @return true si se ha podido logar, false en caso contrario.
	 */
	private boolean logginInfojobs(UserAgent userAgent){
		try {
			userAgent.visit(url_infojobs);
			
			userAgent.doc.getForm(0).setTextField("email", "");
			userAgent.doc.getForm(0).setPassword("password", "");
			userAgent.doc.getForm(0).submit();
			
//			Form form = userAgent.doc.getForm(0);
//			form.setTextField("email", usernameInfojobs);
//			form.setPassword("password", passwordInfojobs);
//			form.submit();
			
			return true;
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error("ResponseException");
			log.error(e.getMessage());
			log.error("HTTP ERROR CODE: " + new Integer(e.getStatusCode()).toString());
			log.error("############");
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error("############");
			log.error(e.getMessage());
			log.error("############");
		}
		
		return false;
	}

	private UserAgent getUserAgent(Portal portal){
		
		Map<String, String> header = new HashMap<>();
		UserAgent userAgent = new UserAgent();
		
		if(portal == Portal.MONSTER){
			return userAgent;
		}else if(portal == Portal.MIL_TRABAJOS){
			userAgent.settings.charset="Cp1252";
			return userAgent;
		}else{
			header.put("accept", "*/*");
			header.put("accept-language", "es,en;q=0.8");
			header.put("accept-encoding", "gzip");
			header.put("connection", "Keep-Alive");
			header.put("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
			
			userAgent.settings.defaultRequestHeaders = header;
			
			return userAgent;
		}
	}
	
}
