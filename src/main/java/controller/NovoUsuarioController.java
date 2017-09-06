package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
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
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		if(tipoDePerfil.equals(PERFIL_ALUNO)){
			new AlunoDAO().salvar(new Aluno(nome, email, senha));
			
			try {				
				externalContext.redirect("aluno/home.xhtml");
			} 
			catch (IOException e) {  }			
		}else{
			new ProfessorDAO().salvar(new Professor(nome, email, senha));
			try {				
				externalContext.redirect("professor/home.xhtml");
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
