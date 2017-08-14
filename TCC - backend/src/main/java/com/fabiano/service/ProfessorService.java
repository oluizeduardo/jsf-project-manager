package com.fabiano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fabiano.model.Professor;
import com.fabiano.repository.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	ProfessorRepository profRep;
	
	@Transactional
	public void saveProf (Professor prof){
		profRep.save(prof);
	}
	
	@Transactional
	public void deleteProf (Professor prof){
		profRep.delete(prof);
	}
	
	@Transactional
	public void updateProf (Professor prof){
		profRep.save(prof);
	}
	
	@Transactional
	public Professor readByID (Long id){
		return profRep.findOne(id);
	}

}
