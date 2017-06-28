package com.abalia.repo.demanda;

import java.util.List;
import java.util.Map;

import com.abalia.model.PerfilesCount;
import com.abalia.model.demanda.Demanda;

public interface DemandaRepositoryCustom {

	List<PerfilesCount> getPerfiles();

	Map<String, Long> historico(String titulo);

	long contarConocimientos(String titulo, String conocimiento);

	List<Demanda> findByConocimientos(List<String> conocimientos);

}