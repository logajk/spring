package com.vector.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vector.model.WkstWorkstation;

public interface WorkstationRepository extends JpaRepository<WkstWorkstation, String> {

	@Query("SELECT w FROM WkstWorkstation w JOIN FETCH w.grupos")
	public List<WkstWorkstation> findWithGroup();
}
