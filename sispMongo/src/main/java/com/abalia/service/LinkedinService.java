package com.abalia.service;

import java.util.List;

import com.abalia.model.linkedin.Linkedin;

public interface LinkedinService {

	void save(Linkedin linkedin);

	Linkedin findById(String id);

	List<Linkedin> findAll();

	Linkedin findById_demanda(String id_demanda);

}