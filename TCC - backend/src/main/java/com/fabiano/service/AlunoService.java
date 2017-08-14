package com.fabiano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabiano.model.Aluno;
import com.fabiano.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	AlunoRepository alunoRep;
	
	@Transactional
	public void saveAluno (Aluno aluno){
		alunoRep.save(aluno);
	}
	
	@Transactional
	public void deleteAluno(Aluno aluno){
		alunoRep.delete(aluno);
	}
	
	@Transactional
	public void updateAluno(Aluno aluno){
		alunoRep.save(aluno);
	}
	
	@Transactional
	public Aluno readByID(Long id){
		return alunoRep.findOne(id);
	}
}
