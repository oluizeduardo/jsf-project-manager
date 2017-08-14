package com.fabiano.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.fabiano.model.Professor;

public interface ProfessorRepository extends GraphRepository<Professor> {
	
	List<Professor> findByName(String name);

}
