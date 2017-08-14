package com.fabiano.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Professor {
	
	private Long id;
	private String name;
	
	public Professor(){}
	
	private Professor (String name, Long id){
		this.name = name;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
