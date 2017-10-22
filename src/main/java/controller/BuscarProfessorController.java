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
	
	// Diz se a lista de professores está vazia.
	private boolean professoresVazio = true;
	
	
	
	
	public BuscarProfessorController() {
		this.todosProfessores = new ProfessorDAO().listar();
	}
	
	
	
	/**
	 * Diz se a lista com todos os professores está vazia ou não.
	 */
	public boolean isProfessoresVazio(){
		this.professoresVazio = (this.todosProfessores.size() < 1);
		return professoresVazio;
	}
	
	public List<Professor> getTodosProfessores() {
		return todosProfessores;
	}
	public void setTodosProfessores(List<Professor> todosProfessores) {
		this.todosProfessores = todosProfessores;
	}
}
