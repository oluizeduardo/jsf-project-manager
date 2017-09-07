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
import web.SessionUtil;

@ManagedBean(name = "novoUsuarioController")
@ViewScoped
public class NovoUsuarioController {

	private final String PERFIL_ALUNO = "Aluno";
	private final String PERFIL_PROFESSOR = "Professor";
	
	
	// Objeto pessoa salva os dados iniciais do novo usuário.
	private Pessoa novoUsuario = null;
	
	
	
	public NovoUsuarioController() {
		this.novoUsuario = new Pessoa();
	}

	
	
	
	/**
	 * Verifica se o novo usuário é um Aluno ou Professor.
	 * Redireciona para o cadastro correto.
	 */
	public void salvarNovoUsuario(){
		String nome  = novoUsuario.getNome();
		String email = novoUsuario.getContato().getEmail();
		String senha = novoUsuario.getSenha();
		String papel = novoUsuario.getPapel();//Papel: Aluno ou Professor.
		
		String tipoDeUsuario = "";// "aluno" ou "professor".
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
				
		if(papel.equals(PERFIL_ALUNO)){
			new AlunoDAO().salvar(new Aluno(nome, email, senha));
			tipoDeUsuario = "aluno";
			
		}else if(papel.equals(PERFIL_PROFESSOR)){
			new ProfessorDAO().salvar(new Professor(nome, email, senha));
			tipoDeUsuario = "professor";
		}
		
		// Inicia uma sessão para um Aluno.
		SessionUtil.setParam(SessionUtil.KEY_SESSION, novoUsuario);
		
		
		try {				
			externalContext.redirect(tipoDeUsuario+"/home.xhtml");
		} 
		catch (IOException e) {  }	
	}
	
	
	
	
	public Pessoa getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Pessoa novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	
}
