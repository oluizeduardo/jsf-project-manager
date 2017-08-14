package com.fabiano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fabiano.model.Project;
import com.fabiano.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projRep;
	
	@Transactional
	public void saveProj (Project proj){
		projRep.save(proj);
	}
	
	@Transactional
	public void deleteProj (Project proj){
		projRep.delete(proj);
	}
	
	@Transactional
	public void updateProj (Project proj){
		projRep.save(proj);
	}
	
	@Transactional
	public Project readByID (Long id){
		return projRep.findOne(id);
	}

}
