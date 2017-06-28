package com.abalia.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;
import com.abalia.model.demanda.Demanda;
import com.abalia.model.demanda.DemandaExt;
import com.abalia.model.oferta.Oferta;

public interface OfertaService {

	List<Oferta> findAll();

	void insertOferta(Oferta oferta);

	void insertOferta(List<Oferta> ofertas);
	
	List<PerfilesCount> getPerfiles(Portal portal);

	void updateOferta(Oferta oferta);

	List<String> getRequisitosByOferta(String titulo);

	long contarRequisitos(String titulo, String requisito);

	Map<String, Long> historico(String titulo);

	List<Oferta> findByRequisitos(List<String> requisitos);

	List<DemandaExt> findCandidato(String id);

	boolean exists(String id);

}