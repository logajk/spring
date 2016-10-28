package com.vector.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vector.model.WkstGroup;

public interface GroupRepository extends JpaRepository<WkstGroup, Integer> {

	@Query("SELECT w FROM WkstGroup w JOIN FETCH w.workstations")
	public List<WkstGroup> findWithWorkStations();
}
