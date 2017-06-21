package com.abalia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abalia.model.Demanda;
import com.abalia.model.Oferta;
import com.abalia.model.OfertaExt;
import com.abalia.model.PerfilesCount;
import com.abalia.repo.DemandaRepository;
import com.abalia.repo.OfertaRepository;

@Service
public class DemandaServiceImpl implements DemandaService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DemandaRepository demandaRepository;
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	/* (non-Javadoc)
	 * @see com.abalia.service.DemandaService#insert(com.abalia.model.Demanda)
	 */
	@Override
	public void insert(Demanda demanda){
		demandaRepository.save(demanda);
	}
	
	@Override
	public void insert(List<Demanda> listado){
		demandaRepository.save(listado);
	}
	
	@Override
	public List<Demanda> findAll(){
		return demandaRepository.findAll();
	}
	
	@Override
	public Demanda findById(String id){
		return demandaRepository.findOne(id);
	}
	
	@Override
	public boolean exists(String id){
		return demandaRepository.exists(id);
	}
	
	@Override
	public List<PerfilesCount> getPerfiles(){
		return demandaRepository.getPerfiles();
	}
	
	@Override
	public Map<String, Long> historico(String titulo){
		return demandaRepository.historico(titulo);
	}
	
	@Override
	public void updateFechaAlta(String id, Date fecha){
		Demanda demanda = demandaRepository.findOne(id);
		
		demanda.setFechaAlta(fecha);
		
		insert(demanda);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getConocimientosByDemanda(String titulo){
		Collection<String> listado = new ArrayList<>();
		
		List<Demanda> listadoDemandas = demandaRepository.findByTitulo(titulo);
		
		for(Demanda d : listadoDemandas){
			listado = CollectionUtils.union(listado, d.getConocimientos());
		}
		
		return (List<String>) listado;
	}
	
	@Override
	public long contarConocimientos(String titulo, String conocimiento){
		return demandaRepository.contarConocimientos(titulo, conocimiento);
	}
	
	@Override
	public List<OfertaExt> findOferta(String id, float notaCorte){
		Demanda demanda = demandaRepository.findOne(id);
		
		List<Oferta> listaOferta = ofertaRepository.findByRequisitosIn(new ArrayList<>(demanda.getConocimientos()));
		
		List<OfertaExt> listaOfertaExt = new ArrayList<>();
		
		for(Oferta o : listaOferta){
			listaOfertaExt.add(new OfertaExt(o));
		}
		
		BigDecimal puntuacion;
		//Se valora cada oferta
		for(int i=0; i<listaOfertaExt.size();i++){
			puntuacion = new BigDecimal(CollectionUtils.intersection(demanda.getConocimientos(), listaOfertaExt.get(i).getRequisitos()).size());
			puntuacion = puntuacion.multiply(new BigDecimal(100));
			puntuacion = puntuacion.divide(new BigDecimal(listaOfertaExt.get(i).getRequisitos().size()), 2, BigDecimal.ROUND_HALF_UP);
			log.debug("Puntuacion obtenida: "+ puntuacion);
			listaOfertaExt.get(i).setPuntuacion(puntuacion);
		}
		
		Collections.sort(listaOfertaExt, new Comparator<OfertaExt>() {

			@Override
			public int compare(OfertaExt o1, OfertaExt o2) {
				// TODO Auto-generated method stub
				return o2.getPuntuacion().compareTo(o1.getPuntuacion());
			}
		});
		
		
		
		return listaOfertaExt.stream().filter(item -> item.getPuntuacion().longValue() >= notaCorte).collect(Collectors.toList());
	}
}
