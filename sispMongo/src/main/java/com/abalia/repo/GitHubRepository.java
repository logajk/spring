package com.abalia.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.GitHub;

public interface GitHubRepository extends MongoRepository<GitHub, String> {

}
