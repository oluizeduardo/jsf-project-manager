package com.fabiano.model;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Aluno {
	
	private String name;
	private Long id;
	
	public Aluno (){}
	
	public Aluno (Long id, String name){
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
