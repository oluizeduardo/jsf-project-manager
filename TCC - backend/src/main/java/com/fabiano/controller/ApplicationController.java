package com.fabiano.controller;

import javax.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fabiano.model.Aluno;
import com.fabiano.model.Professor;
import com.fabiano.model.Project;
import com.fabiano.service.AlunoService;
import com.fabiano.service.ProfessorService;
import com.fabiano.service.ProjectService;

@Controller
public class ApplicationController {
	
	@Autowired
	AlunoService alunoService;
	
	@Autowired
	ProfessorService profService;
	
	@Autowired
	ProjectService projService;
	
	@RequestMapping(value = "/")
	public String home(){
		return "home2";
	}
	
	//Ações referente ao objeto ALUNO
	
	@GET
	@RequestMapping(value = "/saveAluno")
	public String save(){
		Aluno aluno = new Aluno();
		aluno.setName("Marcos Júnior");
		
		alunoService.saveAluno(aluno);
		
		return "home";
	}
	
	@GET
	@RequestMapping(value = "/deleteAluno")
	public String delete (){
		Aluno aluno = new Aluno();
		aluno.setId(00L);
		
		alunoService.deleteAluno(aluno);
		
		return "delete";
	}
	
	@GET
	@RequestMapping(value = "/updateAluno")
	public String updateAluno(){
		Aluno aluno = alunoService.readByID(04L);
		aluno.setName("Luiz Robertinho");
		
		alunoService.updateAluno(aluno);
		
		return "updateAluno";
	}
	
	//Ações referente ao objeto PROFESSOR
	
	@GET
	@RequestMapping(value = "/deleteProf")
	public String deleteProf(){
		Professor prof = new Professor();
		
		prof.setId(06L);
		
		profService.deleteProf(prof);
		
		return "deleteprof";
	}
	
	@GET
	@RequestMapping(value = "/saveProf")
	public String saveProf(){
		Professor prof = new Professor();
		
		prof.setName("Zé Piromba");
		
		profService.saveProf(prof);
		
		return "salvouprof";
	}
	
	@GET
	@RequestMapping(value = "updateProf")
	public String updateProf(){
		Professor prof = profService.readByID(00L);
		prof.setName("Estevan");
		
		profService.updateProf(prof);
		
		return "updateProf";
		
	}
	
	//Ações referente ao objeto PROJETO;
	
	@GET
	@RequestMapping(value = "/deleteProj")
	public String deleteProj(){
		Project proj = new Project();
		
		proj.setId(9L);
		
		projService.deleteProj(proj);
		
		return "deleteProj";
	}
	
	@GET
	@RequestMapping(value = "/saveProj")
	public String saveProj(){
		Project proj = new Project();
		
		proj.setName("Vida");
		
		projService.saveProj(proj);
		
		return "saveProj";
	}
	
	@GET
	@RequestMapping(value = "updateProj")
	public String updateProj(){
		Project proj = projService.readByID(06L);
		
		proj.setName("Viagem");
		
		projService.updateProj(proj);
		
		return "updateProj";
		
	}

}
