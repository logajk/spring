package com.abalia.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.Demanda;

public interface DemandaRepository extends MongoRepository<Demanda, String>, DemandaRepositoryCustom {

	public List<Demanda> findByTitulo(String titulo);
}
