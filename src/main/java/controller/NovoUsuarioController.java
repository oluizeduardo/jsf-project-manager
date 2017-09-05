package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.dao.AlunoDAO;
import model.dao.ProfessorDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import model.pojo.Professor;

@ManagedBean(name = "novoUsuarioController")
@ViewScoped
public class NovoUsuarioController {

	private final String PERFIL_ALUNO = "Aluno";
	
	
	// Objeto pessoa salva os dados iniciais do novo usuário.
	private Pessoa novoUsuario = null;
	
	// Necessária para se saber que tipo de objeto deve ser cadastrado.
	private String tipoDePerfil = null;
	
	
	
	public NovoUsuarioController() {
		this.novoUsuario = new Pessoa();
	}

	
	
	
	/**
	 * Verifica se o novo usuário é um Aluno ou Professor.
	 * Redireciona para o cadastro correto.
	 */
	public void salvarNovoUsuario(){
		String nome = novoUsuario.getNome();
		String email = novoUsuario.getContato().getEmail();
		String senha = novoUsuario.getSenha();
		
		if(tipoDePerfil.equals(PERFIL_ALUNO)){
			System.out.println("Cadastro de um novo Aluno");
			new AlunoDAO().salvarAluno(new Aluno(nome, email, senha));
			
			try {
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
			} 
			catch (IOException e) {  }			
		}else{
			System.out.println("Cadastro de um novo Professor");
			new ProfessorDAO().salvarProfessor(new Professor(nome, email, senha));
			try {
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("professor/home.xhtml");
			} 
			catch (IOException e) {  }
		}
	}
	
	
	
	
	public Pessoa getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Pessoa novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	public String getTipoDePerfil() {
		return tipoDePerfil;
	}

	public void setTipoDePerfil(String tipoDePerfil) {
		this.tipoDePerfil = tipoDePerfil;
	}
	
	
	
	
}
