package com.abalia.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abalia.model.Demanda;
import com.abalia.model.OfertaExt;
import com.abalia.model.PerfilesCount;

public interface DemandaService {

	void insert(Demanda demanda);

	List<Demanda> findAll();

	Demanda findById(String id);

	boolean exists(String id);

	List<PerfilesCount> getPerfiles();

	Map<String, Long> historico(String titulo);

	void updateFechaAlta(String id, Date fecha);

	List<String> getConocimientosByDemanda(String titulo);

	long contarConocimientos(String titulo, String conocimiento);

	void insert(List<Demanda> listado);

	List<OfertaExt> findOferta(String id, float notaCorte);

}