package com.abalia.repo.github;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abalia.model.github.GitHub;

public interface GitHubRepository extends MongoRepository<GitHub, String> {

}
