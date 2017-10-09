package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import model.dao.ProfessorDAO;
import model.pojo.Professor;

@ManagedBean
public class BuscarProfessorController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// Lista com todos os professores cadastrados no sistema.
	private List<Professor> todosProfessores = null;
	
	
	public BuscarProfessorController() {
		this.todosProfessores = new ProfessorDAO().listar();
	}
	
	
	public List<Professor> getTodosProfessores() {
		return todosProfessores;
	}
	public void setTodosProfessores(List<Professor> todosProfessores) {
		this.todosProfessores = todosProfessores;
	}
}
