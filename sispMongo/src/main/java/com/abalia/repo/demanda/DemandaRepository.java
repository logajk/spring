package com.abalia.repo.demanda;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.demanda.Demanda;

public interface DemandaRepository extends MongoRepository<Demanda, String>, DemandaRepositoryCustom {

	public List<Demanda> findByTitulo(String titulo);
}
