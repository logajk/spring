package com.vector.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vector.model.WkstStatus;

public interface StatusRepository extends JpaRepository<WkstStatus, String> {

}
