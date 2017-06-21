package com.abalia.service;

import java.util.List;

import com.abalia.model.GitHub;

public interface GitHubService {

	List<GitHub> findAll();

	GitHub findById(String id);

	boolean exists(String id);

	void save(GitHub gitHub);

}