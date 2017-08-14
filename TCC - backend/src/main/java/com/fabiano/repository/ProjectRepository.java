package com.fabiano.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.fabiano.model.Project;

public interface ProjectRepository extends GraphRepository<Project> {
	List<Project> findByName (String name);
}
