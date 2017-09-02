package controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.pojo.Professor;
import model.dao.ProfessorDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;
import model.helperView.ListaDeIdiomas;


@ManagedBean(name = "cadastrarProfessorController")
@ViewScoped
public class CadastrarProfessorController {

	private Professor professor = null;
	private List<String> estadosBrasileiros = null;
	private List<String> estadoCivil = null;
	private List<String> idiomas = null;
	private ProfessorDAO profDAO = new ProfessorDAO();
	
	public CadastrarProfessorController() {
		this.professor = new Professor();
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
	}
	

	public void salvar(){
		
		System.out.println("Salvando professor: " + professor);
		profDAO = new ProfessorDAO();
		profDAO.salvarProfessor(professor);
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("professor/home.xhtml");
		} catch (IOException e) { 
			System.err.println(e.getMessage());
		}
	}
	
	public Professor getProfessor() {
		return professor;
	}


	public void setAluno(Professor prof) {
		this.professor = prof;
	}
	
	public List<String> getEstadosBrasileiros() {
		return estadosBrasileiros;
	}
	
	public List<String> getEstadoCivil() {
		return estadoCivil;
	}
	
	public List<String> getIdiomas() {
		return idiomas;
	}
	
	
}
