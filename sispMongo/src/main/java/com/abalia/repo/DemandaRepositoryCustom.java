package com.abalia.repo;

import java.util.List;
import java.util.Map;

import com.abalia.model.Demanda;
import com.abalia.model.PerfilesCount;

public interface DemandaRepositoryCustom {

	List<PerfilesCount> getPerfiles();

	Map<String, Long> historico(String titulo);

	long contarConocimientos(String titulo, String conocimiento);

	List<Demanda> findByConocimientos(List<String> conocimientos);

}