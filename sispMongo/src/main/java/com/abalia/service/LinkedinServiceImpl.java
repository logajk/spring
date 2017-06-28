package com.abalia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abalia.model.linkedin.Linkedin;
import com.abalia.repo.linkedin.LinkedinRepository;

@Service
public class LinkedinServiceImpl implements LinkedinService {

	@Autowired
	LinkedinRepository linkedinRepository;
	
	@Override
	public void save(Linkedin linkedin){
		linkedinRepository.save(linkedin);
	}
	
	@Override
	public Linkedin findById(String id){
		return linkedinRepository.findOne(id);
	}
	
	@Override
	public List<Linkedin> findAll(){
		return linkedinRepository.findAll();
	}

	@Override
	public Linkedin findById_demanda(String id_demanda) {
		// TODO Auto-generated method stub
		return linkedinRepository.findById_demanda(id_demanda);
	}
}
