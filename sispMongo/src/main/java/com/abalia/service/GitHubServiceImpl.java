package com.abalia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.abalia.model.github.GitHub;
import com.abalia.repo.github.GitHubRepository;

@Service
public class GitHubServiceImpl implements GitHubService {

	@Autowired
	private GitHubRepository repository;
	
	/* (non-Javadoc)
	 * @see com.abalia.service.GitHubService#findAll()
	 */
	@Override
	public List<GitHub> findAll(){
		return repository.findAll();
	}
	
	@Override
	public List<GitHub> findAllSortByFollowers(){
		return repository.findAll(new Sort(Sort.Direction.DESC, "followers"));
	}
	
	@Override
	public GitHub findById(String id){
		return repository.findOne(id);
	}
	
	@Override
	public void save(GitHub gitHub){
		repository.save(gitHub);
	}
	
	@Override
	public boolean exists(String id){
		return repository.exists(id);
	} 
}
