package com.abalia.repo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.abalia.model.Oferta;
import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;

public interface OfertaRepositoryCustom {

	/**
	 * Devuelve el listado de perfiles m&aacute;s demandados ordenados descendentemente.
	 * @param portal
	 * @return Listado de perfiles
	 */
	List<PerfilesCount> getPerfiles(Portal portal);

	long contarRequisitos(String titulo, String requisito);

	Map<String, Long> historico(String titulo);

	List<Oferta> findByRequisitosAll(List<String> requisitos);

	List<Oferta> findByRequisitosIn(List<String> requisitos);
}