package com.abalia.repo.oferta;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.oferta.Oferta;

public interface OfertaRepository extends MongoRepository<Oferta, String>, OfertaRepositoryCustom {

	public List<Oferta> findByTitulo(String titulo);
	
}
