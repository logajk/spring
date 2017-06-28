package com.abalia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.abalia.model.github.GitHub;
import com.abalia.service.DemandaService;
import com.abalia.service.GitHubService;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

@RestController
@RequestMapping("/github")
@Api(name="Servicio github", description="Metodos para obterner infomacion de GitHub", visibility=ApiVisibility.PUBLIC, stage=ApiStage.RC)
@ApiVersion(since="1.0")
public class GitHubController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	static final String USUARIO_NO_ENCONTRADO = "No se ha encontrado ningún resultado para";
	static final String NINGUN_RESULTADO = "no obtuvo ningún resultado";
	
	@Autowired
	private GitHubService gitHubService;
	
	@Autowired
	private DemandaService demandaService;
	
	@Value("${url.busqueda.google}")
	private String url_busqueda_google;
	
	
	@RequestMapping(method=RequestMethod.POST)
	@ApiMethod(description="Devuelve las contribuciones del usuario en el portal GITHUB")
	public ResponseEntity<GitHub> getInformation(@ApiQueryParam(name="id", description="ID del demandante de empleo") @RequestParam(value="id")String id){
		
		GitHub gitHub = new GitHub();
		Demanda demanda;
		UserAgent userAgent = getUserAgent();
		
		if(StringUtils.isNumeric(id)){
			if(demandaService.exists(id)){
				demanda = demandaService.findById(id);
				
				 try {
					userAgent.visit(url_busqueda_google);
					userAgent.doc.apply("\""+org.apache.commons.lang3.StringUtils.stripAccents(demanda.getNombre()+" "+demanda.getApellidos())+"\" site:github.com");
					userAgent.doc.submit();
					log.debug(userAgent.doc.innerHTML());
					if(!userAgent.doc.innerText().contains(USUARIO_NO_ENCONTRADO) && !userAgent.doc.innerText().contains(NINGUN_RESULTADO)){
						log.debug("El usuario "+ demanda.getNombre() +" tiene perfil Github encontrado");
						
						//Vamos a recorrer todos los links que aparecen
						Elements listaResultados = userAgent.doc.findEvery("<div class=g>");
						
//						log.debug(userAgent.doc.innerHTML());
						
						log.debug("Se han encontrado " + listaResultados.size() + " resultados");
						
						for(Element e : listaResultados){
							log.debug(e.findFirst("<span class=st>").innerText());
							if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(e.findFirst("<span class=st>").innerText(), demanda.getNombre())){
								log.debug("link: " + e.findFirst("<cite>").innerText());
								gitHub = extractGitHub(userAgent, e.findFirst("<cite>").innerText().trim());
								if(gitHub!=null){
									gitHub.setId_demanda(demanda.getId());
									log.debug(gitHub.toString());
									gitHubService.save(gitHub);
								}else{
									return new ResponseEntity<>(HttpStatus.NOT_FOUND);
								}
							}
						}
					}else{
						log.debug("El usuario "+ demanda.getNombre() +" NO tiene perfil Github encontrado");
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
					
					
				} catch (ResponseException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
					log.error("StatusCode: " + new Integer(e.getStatusCode()).toString());
					log.error("Request URL: "+e.getRequestUrlData());
				} catch (JauntException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
				}
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(gitHub.getId()!=null){
			return new ResponseEntity<GitHub>(gitHub, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping()
	@ApiMethod(description="Devuelve todos los perfiles de usuarios que tengan creada cuenta en GitHub")
	public List<GitHub> getAll(){
		return gitHubService.findAllSortByFollowers();
	}
	
	private UserAgent getUserAgent(){
		
		Map<String, String> header = new HashMap<>();
		UserAgent userAgent = new UserAgent();

		header.put("accept", "*/*");
		header.put("accept-language", "es,en;q=0.8");
		header.put("accept-encoding", "gzip");
		header.put("connection", "Keep-Alive");
		header.put("User-Agent",
				"Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

		// userAgent.settings.defaultRequestHeaders = header;

		userAgent.settings.autoSaveAsHTML = true;
		userAgent.settings.charset = "iso-8859-1";

		return userAgent;
	}
	
	/**
	 * M&eacute;todo encargado de devolver un objeto Github
	 * @param userAgent 
	 * @param link
	 * @return
	 */
	private GitHub extractGitHub(final UserAgent userAgent, String link){
		
		GitHub gitHub = new GitHub();
		
		try {
			userAgent.visit(link);
			
			if (userAgent.getLocation().split("/").length > 4) {
				//Me voy al perfil del autor
				log.debug("Me voy al perfil del autor");
				Element element = userAgent.doc.findFirst("<span class=author>");
				userAgent.visit(element.findFirst("<a>").getAt("href"));
			}
			gitHub.setId(userAgent.doc.findFirst("<span itemprop=additionalName>").getText().trim());
			gitHub.setContribution(userAgent.doc.findFirst("<div class=js-contribution-graph>").findFirst("<h2>").getText().trim().replace("\n", "").replaceAll(" +", " "));
			Elements tabs = userAgent.doc.findEvery("<a role=tab>");
//			log.debug(userAgent.doc.innerHTML());
			
			log.debug("Tengo " + tabs.size() +" pestañas");
			
			if (tabs.size() > 0) {
				gitHub.setRepositories(new Integer(tabs.getElement(1).findFirst("<span class=Counter>").getText().trim()));
				gitHub.setFollowers(new Integer(tabs.getElement(3).findFirst("<span class=Counter>").getText().trim()));
			}
			//Ahora voy a buscar de que tipo de tecnologias son los repositorios
			tabs = userAgent.doc.findEvery("<span class=d-block>");
			log.debug("Tecnologias encontradas " + tabs.size());
			if (tabs.size() > 0) {
				String tecnologia;
				for(Element e : tabs){
					tecnologia = org.apache.commons.lang3.StringUtils.normalizeSpace(e.nextSiblingElement().nextSiblingElement().innerText().trim().replaceAll("\n", "")).split(" ")[0];
					if(StringUtils.isNotEmpty(tecnologia)){
						gitHub.getTecnologias().add(tecnologia);
					}
				}
			}
			return gitHub;
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
}
