package com.fabiano.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.fabiano.model.Aluno;

public interface AlunoRepository extends GraphRepository<Aluno> {
	
	List<Aluno> findByName(String name);

}
