package com.fabiano.model;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Tecnologias {
	
	private String name;
	private Long id;
	
	@Relationship (type = "KNOWS")
	private List<Tecnologias> tecnologias;
	
	public Tecnologias (){}
	
	public Tecnologias (Long id, String name, List<Tecnologias> tecnologias){
		this.id = id;
		this.name = name;
		this.tecnologias = tecnologias;
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

	public List<Tecnologias> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(List<Tecnologias> tecnologias) {
		this.tecnologias = tecnologias;
	}
}
