package com.abalia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abalia.model.tag.Tag;
import com.abalia.repo.tag.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository repository;
	
	/* (non-Javadoc)
	 * @see com.abalia.service.TagService#findAll()
	 */
	@Override
	public List<Tag> findAll(){
		return repository.findAll();
	}
	
	@Override
	public Tag findById(String id){
		return repository.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.abalia.service.TagService#findByTagName(java.lang.String)
	 */
	@Override
	public List<Tag> findByTagName(String TagName){
		return repository.findByTagName(TagName);
	}
	
	@Override
	public List<Tag> save(Tag tag){
	
		repository.save(tag);
		
		return repository.findAll();
	}
	
	@Override
	public void delete(String id){
		repository.delete(id);
	}
}
