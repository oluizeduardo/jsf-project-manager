package com.fabiano.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Project {
	
	private Long id;
	private String name;
	
	public Project(){}
	
	public Project (Long id, String name){
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
