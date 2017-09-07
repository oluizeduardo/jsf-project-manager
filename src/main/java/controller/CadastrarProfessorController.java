package controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.pojo.Professor;
import view.Mensagem;
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
	
	private String senhaAntiga = null;
	private String novaSenha = null;
	
	
	public CadastrarProfessorController() {
		this.professor = new Professor();
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
	}
	

	public void salvarProfessor(){
		
		profDAO = new ProfessorDAO();
		boolean cadastrou = profDAO.salvar(professor);
		
		if(cadastrou){
			Mensagem.ExibeMensagem("Dados atualizados com sucesso!");
		}else{
			Mensagem.ExibeMensagem("Não foi possível atualizar os dados.");
		}
	}
	
	
	
	
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
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
