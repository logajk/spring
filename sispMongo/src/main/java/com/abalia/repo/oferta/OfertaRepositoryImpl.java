package com.abalia.repo.oferta;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import com.abalia.model.PerfilesCount;
import com.abalia.model.Portal;
import com.abalia.model.oferta.Oferta;

public class OfertaRepositoryImpl implements OfertaRepositoryCustom {

	/* (non-Javadoc)
	 * @see com.abalia.repo.OfertaRepositoryCustom#getPerfiles()
	 */
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<PerfilesCount> getPerfiles(Portal portal){
		
		Aggregation agg = newAggregation(
			match(Criteria.where("portal").in(portal)),
			group("titulo").count().as("contador"),
			project("contador").and("titulo").previousOperation(),
			sort(Sort.Direction.DESC, "contador")
		);
		
		AggregationResults<PerfilesCount> results = mongoTemplate.aggregate(agg, Oferta.class, PerfilesCount.class);
		
		return results.getMappedResults();
	}
	
	@Override
	public long contarRequisitos(String titulo, String requisito){
		Query query = new Query();
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("requisitos").in(requisito));
		
		return mongoTemplate.count(query, Oferta.class);
	}
	
	@Override
	public Map<String, Long> historico(String titulo){
		Query query = new Query();
		Date fechaActual = new Date();
		
		Calendar cal = Calendar.getInstance();
		Map<String, Long> historico = new HashMap<>();
		
		
		cal.setTime(fechaActual);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lt(fechaActual).gte(cal.getTime()));
		
		historico.put("1 dia", mongoTemplate.count(query, Oferta.class));
		
		//Buscamos por una semana
		cal.setTime(fechaActual);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		historico.put("1 senama", mongoTemplate.count(query, Oferta.class));
		
		//Buscamos por un mes
		cal.setTime(fechaActual);
		cal.add(Calendar.MONTH, -1);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		historico.put("1 mes", mongoTemplate.count(query, Oferta.class));
		
		//Buscamos por tres mes
		cal.setTime(fechaActual);
		cal.add(Calendar.MONTH, -3);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		historico.put("3 meses", mongoTemplate.count(query, Oferta.class));
		
		//Buscamos por seis mes
		cal.setTime(fechaActual);
		cal.add(Calendar.MONTH, -6);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").in(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		historico.put("6 meses", mongoTemplate.count(query, Oferta.class));
		
		return historico;
	}
	
	@Override
	public List<Oferta> findByRequisitosAll(List<String> requisitos){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("requisitos").all(requisitos));
		
		return mongoTemplate.find(query, Oferta.class);
	}
	
	@Override
	public List<Oferta> findByRequisitosIn(List<String> requisitos){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("requisitos").in(requisitos));
		
		return mongoTemplate.find(query, Oferta.class);
	}
}
