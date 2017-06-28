package com.abalia.repo.demanda;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

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

import com.abalia.model.PerfilesCount;
import com.abalia.model.demanda.Demanda;

public class DemandaRepositoryImpl implements DemandaRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/* (non-Javadoc)
	 * @see com.abalia.repo.DemandaRepositoryCustom#getPerfiles()
	 */
	@Override
	public List<PerfilesCount> getPerfiles(){
		
		Aggregation agg = newAggregation(
			group("titulo").count().as("contador"),
			project("contador").and("titulo").previousOperation(),
			sort(Sort.Direction.DESC, "contador")
		);
		
		AggregationResults<PerfilesCount> results = mongoTemplate.aggregate(agg, Demanda.class, PerfilesCount.class);
		
		return results.getMappedResults();
	}
	
	@Override
	public Map<String, Long> historico(String titulo){
		
		Query query = new Query();
		Date fechaActual = new Date();
		Calendar cal = Calendar.getInstance();
		Map<String, Long> historico = new HashMap<>();
		
		cal.setTime(fechaActual);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		query.addCriteria(Criteria.where("titulo").regex(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		
		historico.put("1 dia", mongoTemplate.count(query, Demanda.class));
		
		//Buscamos por una semana
		cal.setTime(fechaActual);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").regex(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		
		historico.put("1 semana", mongoTemplate.count(query, Demanda.class));
		
		//Buscamos por tres meses
		cal.setTime(fechaActual);
		cal.add(Calendar.MONTH, -3);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").regex(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		
		historico.put("3 meses", mongoTemplate.count(query, Demanda.class));
		
		//Buscamos por 6 meses
		cal.setTime(fechaActual);
		cal.add(Calendar.MONTH, -6);
		query = new Query();
		query.addCriteria(Criteria.where("titulo").regex(titulo));
		query.addCriteria(Criteria.where("fechaAlta").lte(fechaActual).gte(cal.getTime()));
		
		historico.put("6 meses", mongoTemplate.count(query, Demanda.class));
		
		
		return historico;
	}
	
	@Override
	public long contarConocimientos(String titulo, String conocimiento){
		Query query = new Query();
		query.addCriteria(Criteria.where("titulo").regex(titulo).and("conocimientos").in(conocimiento));
		
		return mongoTemplate.count(query, Demanda.class);
	}
	
	@Override
	public List<Demanda> findByConocimientos(List<String> conocimientos){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("conocimientos").in(conocimientos));
		
		return mongoTemplate.find(query, Demanda.class);
	}
}
