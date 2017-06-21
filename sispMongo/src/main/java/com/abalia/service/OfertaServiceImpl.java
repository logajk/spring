package com.abalia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abalia.model.Demanda;
import com.abalia.model.DemandaExt;
import com.abalia.model.Oferta;
import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;
import com.abalia.repo.DemandaRepository;
import com.abalia.repo.OfertaRepository;

@Service
public class OfertaServiceImpl implements OfertaService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@Autowired
	private DemandaRepository demandaRepository;
	
	/* (non-Javadoc)
	 * @see com.abalia.service.OfertaService#findAll()
	 */
	@Override
	public List<Oferta> findAll(){
		return ofertaRepository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.abalia.service.OfertaService#insertOferta(com.abalia.model.Oferta)
	 */
	@Override
	public void insertOferta(Oferta oferta){
		
		if(!ofertaRepository.exists(oferta.getId())){
			ofertaRepository.save(oferta);
		}
	}
	
	@Override
	public void insertOferta(List<Oferta> ofertas){
		
		for(Oferta o : ofertas){
			insertOferta(o);
		}
	}

	@Override
	public List<PerfilesCount> getPerfiles(Portal portal) {
		// TODO Auto-generated method stub
		return ofertaRepository.getPerfiles(portal);
	}
	
	@Override
	public void updateOferta(Oferta oferta){
		ofertaRepository.save(oferta);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getRequisitosByOferta(String titulo){
		Collection<String> listado = new ArrayList<>();
		
		List<Oferta> listadoOferta = ofertaRepository.findByTitulo(titulo);
		
		for(Oferta o : listadoOferta){
			listado = CollectionUtils.union(listado, o.getRequisitos());
		}
		
		return (List<String>) listado;
	}
	
	@Override
	public long contarRequisitos(String titulo, String requisito){
		return ofertaRepository.contarRequisitos(titulo, requisito);
	}
	
	@Override
	public Map<String, Long> historico(String titulo){
		return ofertaRepository.historico(titulo);
	}
	
	@Override
	public List<Oferta> findByRequisitos(List<String> requisitos){
		return ofertaRepository.findByRequisitosAll(requisitos);
	}
	
	@Override
	public boolean exists(String id){
		return ofertaRepository.exists(id);
	}
	
	@Override
	public List<DemandaExt> findCandidato(String id){
		
		Oferta oferta = ofertaRepository.findOne(id);
		
		List<Demanda> listaDemanda = demandaRepository.findByConocimientos(oferta.getRequisitos());
		
		List<DemandaExt> listaDemandaExt = new ArrayList<>();
		
		for(Demanda d : listaDemanda){
		
			listaDemandaExt.add(new DemandaExt(d));
		}
		
		BigDecimal puntuacion;
		//Se valora cada candidatos
		for(int i=0; i<listaDemandaExt.size();i++){
			puntuacion = new BigDecimal(CollectionUtils.intersection(oferta.getRequisitos(), listaDemandaExt.get(i).getConocimientos()).size());
			puntuacion = puntuacion.multiply(new BigDecimal(100));
			puntuacion = puntuacion.divide(new BigDecimal(oferta.getRequisitos().size()), 2, BigDecimal.ROUND_HALF_UP);
			log.debug("Puntuacion obtenida: "+ puntuacion);
			listaDemandaExt.get(i).setPuntuacion(puntuacion);
		}
		
		Collections.sort(listaDemandaExt, new Comparator<DemandaExt>() {

			@Override
			public int compare(DemandaExt o1, DemandaExt o2) {
				// TODO Auto-generated method stub
				return o2.getPuntuacion().compareTo(o1.getPuntuacion());
			}
		});
		
		//Esto es para implementar nota de corte
//		return listaDemandaExt.stream().filter(item -> item.getPuntuacion().longValue() >= 75).collect(Collectors.toList());
		
		return listaDemandaExt;
	}
}
