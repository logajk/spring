package com.abalia.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;
import com.abalia.model.demanda.Demanda;
import com.abalia.model.oferta.OfertaExt;
import com.abalia.model.tag.Tag;
import com.abalia.service.DemandaService;
import com.abalia.service.TagService;
import com.abalia.utils.ComunCollection;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.SearchException;
import com.jaunt.UserAgent;

@RestController
@RequestMapping("/demanda")
@Api(name="Servicvio de demandas", description="M&eacute;todos para gestionar las demandas", visibility=ApiVisibility.PUBLIC, stage=ApiStage.RC)
@ApiVersion(since="1.0")
public class DemandaController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DemandaService demandaService;
	
	@Autowired
	private TagService tagService;
	
	@Value("${url.longin.miltrabajos}")
	private String url_milanuncios;
	
	@Value("${usuario.miltrabajos}")
	private String username_milanuncios;
	
	@Value("${contrasenya.miltrabajos}")
	private String password_milanuncios;
	
	@Value("${url.candidato.miltrabajos}")
	private String url_candidato;
	
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	@ApiMethod(path="insert",
		description="Inserta una nueva demanda",
		produces={MediaType.APPLICATION_JSON_VALUE})	
	public List<Demanda> insertDemanda(){
		
		log.debug("Insertando nueva demanda");
		
		Demanda demanda = new Demanda();
		
		demanda.setId(UUID.randomUUID().toString());
		demanda.setNombre("prueba1");
		demanda.setFechaAlta(new Date());
		demanda.setTitulo("Analista Programador");
		demanda.setPortal(Portal.MONSTER);
		
		demanda.getConocimientos().add("java");
		demanda.getConocimientos().add("ios");
		demanda.getConocimientos().add("windows");
		demanda.getConocimientos().add("unix");
		demanda.getConocimientos().add("go");
		demanda.getConocimientos().add("ant");
		demanda.getConocimientos().add("orm");
		demanda.getConocimientos().add("proxy");
		demanda.getConocimientos().add("io");
		demanda.getConocimientos().add("com");
		
		log.debug("Insertando demanda: "+ demanda.toString());
		
		demandaService.insert(demanda);
		
		demanda.setId(UUID.randomUUID().toString());
		demanda.setNombre("prueba2");
		demanda.setFechaAlta(new Date());
		demanda.getConocimientos().remove("com");
		
		demandaService.insert(demanda);
		
		demanda = new Demanda();
		
		demanda.setId(UUID.randomUUID().toString());
		demanda.setNombre("prueba3");
		demanda.setFechaAlta(new Date());
		demanda.setTitulo("Analista");
		demanda.setPortal(Portal.MONSTER);
		
		demanda.getConocimientos().add("jira");
		demanda.getConocimientos().add("jenkins");
		demanda.getConocimientos().add("sonarqube");
		
		demandaService.insert(demanda);
		
		return demandaService.findAll();
	}
	
	@RequestMapping("/getPerfiles")
	@ApiMethod(description="Obtiene todos los perfiles ordenados por n&uacute;mero de ocurrencias", produces={MediaType.APPLICATION_JSON_VALUE})
	public List<PerfilesCount> getPerfiles(){
		
		return demandaService.getPerfiles();
	}
	
	@RequestMapping("/getHistorico")
	@ApiMethod(description="Devuelve el hist&oacute;rico de un perfil", produces={MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Long> getHistorico (@ApiQueryParam(name="titulo", description="Titulo del perfil a buscar") @RequestParam(value="titulo") String titulo){
		
		return demandaService.historico(titulo);
	}
	
	@RequestMapping(value="/updateFechaAlta", method=RequestMethod.PUT)
	@ApiMethod(description="Actualiza la fecha de alta de un perfil", produces={MediaType.APPLICATION_JSON_VALUE})
	public Demanda updateFechaAlta(
			@ApiQueryParam(name="id", description="ID del perfil a modificar fecha") @RequestParam(value="id") String id,
			@ApiQueryParam(name="fecha", description="Fecha a modificar", format="ddMMyyyy_HHmmss") @RequestParam(value="fecha") @DateTimeFormat(pattern="ddMMyyyy_HHmmss") Date fecha){
		
		log.debug("Fecha recibida es: "+fecha);
		
		demandaService.updateFechaAlta(id, fecha);
		
		return demandaService.findById(id);
	}
	
	@RequestMapping("/getById")
	@ApiMethod(description="Se busca demanda por ID", produces=MediaType.APPLICATION_JSON_VALUE)
	public Demanda findById(@ApiQueryParam(name="id", description="ID del perfil a buscar") @RequestParam(value="id") String id){
		return demandaService.findById(id);
	}
	
	@RequestMapping("/getAll")
	@ApiMethod(description="Devuelve todas las demandas", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Demanda> getAll(){
		return demandaService.findAll();
	}
	
	@RequestMapping("/getConocimientosByDemanda")
	@ApiMethod(description="Devuelve todos los conocimientos para una demanda dada")
	public List<String> getConocimientosByDemanda(@ApiQueryParam(name="titulo", description="Titulo de la demanda a buscar") @RequestParam(value="titulo") String titulo){
		
		return demandaService.getConocimientosByDemanda(titulo);
	}
	
	@RequestMapping("/getFrecuenciaConocimientosByDemanda")
	@ApiMethod(description="Devuelve todos los conocimientos para una demanda dada ordenada por ocurrencia de conocimientos")
	public Map<String, Long> getFrecuenciaConocimientosByDemanda(@ApiQueryParam(name="titulo", description="T&iacute;tulo de la demanda a buscar") @RequestParam(value="titulo") String titulo){
		HashMap<String, Long> mapa = new HashMap<>();
		
		List<String> listado = new ArrayList<>();
		
		if(!StringUtils.isEmpty(titulo)){
			listado = demandaService.getConocimientosByDemanda(titulo);
		}
		
		for(String s : listado){
			mapa.put(s, demandaService.contarConocimientos(titulo, s));
		}
		
		return ComunCollection.sortMapByValue(mapa);
	}
	
	@SuppressWarnings("hiding")
	@RequestMapping(value="/insertCandidatos", method=RequestMethod.POST)
	@ApiMethod(description="Inserta todos los candidatos posibles para el portal definido")
	public List<Demanda> insertCandidatos(@ApiQueryParam(name="portal", description="Portal desde donde se va a obtener los candidatos", allowedvalues={"MONSTER","INFOJOBS", "MIL_TRABAJOS"}) @RequestParam(value="portal") String portal){
		
		UserAgent userAgent = getUserAgent(Portal.valueOf(portal));
		List<Demanda> listado = new ArrayList<>();
		Elements offerList;
		Demanda demanda;
		String[] split;
		int i=1;
		
		if(Portal.MIL_TRABAJOS.toString().equals(portal) && logginMilAnuncios(userAgent)){
			log.debug("Voy a buscar candidatos");
			
			try {
				log.debug(userAgent.doc.innerHTML());
				Element element = userAgent.doc.findFirst("<font>Buscador").getParent();
				log.debug("Link Buscador C.V encontrado");
				log.debug("link: "+element.getAt("href"));
				userAgent.visit(element.getAt("href"));
				
				//Rellenamos el formulario con pais espanya y provincia madrid.
				log.debug("Numero de option pais encontrados son: "+userAgent.doc.findFirst("<select name=pais>").findEvery("<option>").size());
//				log.debug("Texto:"+ userAgent.doc.findFirst("<select name=pais>").findEvery("<option>").getElement(1).getText());
				userAgent.doc.findFirst("<select name=pais>").innerHTML("<option value='ESPAÑA'>ESPAÑA</option>");
				log.debug(userAgent.doc.findFirst("<select name=pais>").innerHTML());
				
				userAgent.doc.getForm("<form name=frm_buscadorcv>").setSelect("pais", "ESPAÑA");
				userAgent.doc.getForm("<form name=frm_buscadorcv>").setTextField("palabra1", "java");
				userAgent.doc.getForm("<form name=frm_buscadorcv>").setSelect("provincia", 28);
				
				userAgent.doc.findFirst("<input value=BUSCAR").getParent().innerHTML("<input name='boton' type='submit' value='BUSCAR'></input>");
				
				log.debug("Boton: "+userAgent.doc.findFirst("<input value=BUSCAR").getParent().innerHTML());
				
//				userAgent.doc.getForm("<form name=frm_buscadorcv>").submit(0);
				
				List<BasicNameValuePair> paramentros = new ArrayList<>();
				
//				paramentros.add(new BasicNameValuePair("pais", "OTROS"));
//				paramentros.add(new BasicNameValuePair("paisf", "no"));
				paramentros.add(new BasicNameValuePair("pais", "ESPAÑA"));
				paramentros.add(new BasicNameValuePair("paisf", "si"));
				paramentros.add(new BasicNameValuePair("palabra1", "java"));
				paramentros.add(new BasicNameValuePair("palabra2", ""));
//				paramentros.add(new BasicNameValuePair("provincia", "0"));
				paramentros.add(new BasicNameValuePair("provincia", "28"));
				paramentros.add(new BasicNameValuePair("nivel_estudios", "0"));
				paramentros.add(new BasicNameValuePair("fc", "0"));
				paramentros.add(new BasicNameValuePair("areas", "0"));
				paramentros.add(new BasicNameValuePair("areasf", ""));
				paramentros.add(new BasicNameValuePair("subareas", "0"));
				paramentros.add(new BasicNameValuePair("idioma", "0"));
				paramentros.add(new BasicNameValuePair("idiomaf", ""));
				paramentros.add(new BasicNameValuePair("nivel", "0"));
				paramentros.add(new BasicNameValuePair("carnet", ""));
				paramentros.add(new BasicNameValuePair("coche", ""));
				paramentros.add(new BasicNameValuePair("viajar", ""));
				paramentros.add(new BasicNameValuePair("exp_laboral", "0"));
				paramentros.add(new BasicNameValuePair("foto", ""));
				paramentros.add(new BasicNameValuePair("infor1", ""));
				paramentros.add(new BasicNameValuePair("infor2", ""));
				paramentros.add(new BasicNameValuePair("infor3", ""));
				paramentros.add(new BasicNameValuePair("cambio", ""));
				paramentros.add(new BasicNameValuePair("internet", "0"));
				paramentros.add(new BasicNameValuePair("internet_otros", "Otros..."));
				paramentros.add(new BasicNameValuePair("texto", "0"));
				paramentros.add(new BasicNameValuePair("texto_otros", "Otros..."));
				paramentros.add(new BasicNameValuePair("calculo", "0"));
				paramentros.add(new BasicNameValuePair("calculo_otros", "Otros..."));
				paramentros.add(new BasicNameValuePair("presentacion", "0"));
				paramentros.add(new BasicNameValuePair("presentacion_otros", "Otros..."));
				paramentros.add(new BasicNameValuePair("bd", "0"));
				paramentros.add(new BasicNameValuePair("bd_otros", "Otros..."));
				paramentros.add(new BasicNameValuePair("so", "0"));
				paramentros.add(new BasicNameValuePair("so_otros", "Otros..."));
				
				log.debug(URLEncodedUtils.format(paramentros, "Cp1252"));
				
				userAgent.sendPOST("http://www.miltrabajos.com/buscador/rbuscador_cv.asp", URLEncodedUtils.format(paramentros, "Cp1252"));
				
				log.debug(userAgent.doc.innerText());
				//Ya tenemos la lista de candidatos
				//vamos a recorrerla
				
				do{
					log.debug(userAgent.getLocation());
//					log.debug(userAgent.doc.innerText());
					offerList = userAgent.doc.findEvery("<img src=http://www.miltrabajos.com/img/ficha.gif>");
					
					if(offerList!=null && offerList.size()!=0){
						log.debug("Candidatos encontrados: " + offerList.size());
						
						for(Element e : offerList){
							split = e.getParent().getParent().getParent().getAt("onclick").split("','");
							log.debug("ID candidato obtenido es: " + split[0].substring((split[0].indexOf("=")+1)));
							demanda = extractCandidato(userAgent, split[0].substring((split[0].indexOf("=")+1)));
							if(demanda!=null){
								demandaService.insert(demanda);
								listado.add(demanda);
							}
//							demandaService.insert(listado);
						}
					}
					log.debug("Estoy en la pagina: "+i);
					i++;
					userAgent.sendPOST("http://www.miltrabajos.com/buscador/rbuscador_cv.asp"+"?pag="+i, URLEncodedUtils.format(paramentros, "Cp1252"));
				}while(offerList.size() > 0);
				
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				log.error("############");
				log.error(e.getMessage());
				log.error("############");
			} catch (ResponseException e) {
				// TODO Auto-generated catch block
				log.error("############");
				log.error("ResponseException");
				log.error(e.getMessage());
				log.error("HTTP ERROR CODE: " + new Integer(e.getStatusCode()).toString());
				log.error("############");
			} catch (SearchException e) {
				// TODO Auto-generated catch block
				log.error("############");
				log.error(e.getMessage());
				log.error("############");
			}
		}else{
			log.error("No se ha podido logear en el portal");
		}
		log.debug("Hemos encontrado " + listado.size() + " demandas");
		return listado;
	}
	
	@RequestMapping("/getOfertas")
	@ApiMethod(description="Devuelve todas las ofertas posibles que cumpla el candidato a los requisitos de la oferta")
	public List<OfertaExt> getOfertas(@ApiQueryParam(description="ID de la demanda a buscar") @RequestParam(value="id")String id, @ApiQueryParam(description="Nota de corte para filtrar el resultado")@RequestParam(value="notaCorte", required=false) Float notaCorte){
		if(notaCorte!=null){
			return demandaService.findOferta(id, notaCorte);
		}else{
			return demandaService.findOferta(id, 0);
		}
	}
	
	/**
	 *M&eacute;todo encargado de extraer la informaci&oacute;n de un cadidato 
	 * @param userAgent Objetdo sobre la que se lanzará la busqueda.
	 * @param id ID del cadidato que se va a buscar.
	 * @return Devuelve el objeto Demanda en caso de no poder montar el objeto se devolvera null.
	 */
	private Demanda extractCandidato(final UserAgent userAgent, String id){
		
		Demanda demanda = new Demanda();
		
		try {
			if(!demandaService.exists(id)){
				userAgent.visit(url_candidato+id);
				
				Element email = userAgent.doc.findFirst("<b>E-Mail").getParent().nextSiblingElement();
				Element nombre = userAgent.doc.findFirst("<b>Nombre").getParent().nextSiblingElement();
				Element apellidos = userAgent.doc.findFirst("<b>Apellidos").getParent().nextSiblingElement();
				
				if(!email.getText().trim().equals("Información NO disponible") && !nombre.getText().trim().equals("Información NO disponible") && !apellidos.getText().trim().equals("Información NO disponible")){
					log.debug("Información diponible");
					demanda.setId(id);
					demanda.setEmail(email.findFirst("<a>").getText());
					email = userAgent.doc.findFirst("<b>Nombre").getParent().nextSiblingElement();
					demanda.setNombre(email.getText().trim());
					email = userAgent.doc.findFirst("<b>Apellidos").getParent().nextSiblingElement();
					demanda.setApellidos(email.getText().trim());
					demanda.setFechaAlta(new Date());
					demanda.setPortal(Portal.MIL_TRABAJOS);
					
					Elements elementos = userAgent.doc.findEvery("<b>Nivel de Estudios");
					
					if(elementos.size() > 0){
						demanda.setTitulo(elementos.getElement(1).getParent().nextSiblingElement().getText());
					}
					String requisitos = userAgent.doc.innerText();
					
					for(Tag t : tagService.findAll()){
						if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(requisitos, t.getTagName())){
							demanda.getConocimientos().add(t.getTagName());
						}
					}
				}else{
					log.debug("Información NO disponible");
					return null;
				}
				return demanda;
			}else{
				return null;
			}
			
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param portal Portal sobre la que se va a generar el objeto UserAgent
	 * @return Objeto UserAgent
	 */
	private UserAgent getUserAgent(Portal portal){
		
		Map<String, String> header = new HashMap<>();
		UserAgent userAgent = new UserAgent();
		
		log.debug("Se genera userAgent para: "+portal);
		
		if(portal != Portal.MIL_TRABAJOS){
			return userAgent;
		}else{
			log.debug("Metiendo cabecera");
			header.put("accept", "*/*");
			header.put("accept-language", "es,en;q=0.8");
			header.put("accept-encoding", "gzip");
			header.put("connection", "Keep-Alive");
			header.put("User-Agent", "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
			
			userAgent.settings.defaultRequestHeaders = header;
			userAgent.settings.charset="Cp1252";
			
			return userAgent;
		}
	}
	
	/**
	 * M&eacute;todo encargado de logearse en el portal Mil Anuncios
	 * @param userAgent 
	 * @return "true" si se consigue logear false en caso contrario
	 */
	private boolean logginMilAnuncios(UserAgent userAgent){
		
		try {
			userAgent.visit(url_milanuncios);
			
			userAgent.doc.getForm("<form name=frm_empresalogin>").setTextField("txtEmail", username_milanuncios);
			userAgent.doc.getForm("<form name=frm_empresalogin>").setPassword("txtClave", password_milanuncios);
			userAgent.doc.getForm("<form name=frm_empresalogin>").submit();
			log.debug("Me he podido logear correctamente!!!");
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
}
