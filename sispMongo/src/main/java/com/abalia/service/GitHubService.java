package com.abalia.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.abalia.model.github.GitHub;

public interface GitHubService {

	List<GitHub> findAll();

	GitHub findById(String id);

	boolean exists(String id);

	void save(GitHub gitHub);

	List<GitHub> findAllSortByFollowers();

}