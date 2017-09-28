package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Pessoa;
import model.pojo.Professor;
import web.SessionUtil;



@ManagedBean(name = "homeProfessorController")
@ViewScoped
public class HomeProfessorController implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	// Guarda os valores do usuário que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	// Usuário professor da sessão atual.
	private Professor userProfessor;
	
	
	
	
	
	public HomeProfessorController() { 		
		// Recupera os dados da Session para apresentação no painel de perfil.
		this.pessoaSession = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		if(pessoaSession != null){
			userProfessor = new Professor();
			userProfessor.setId(pessoaSession.getId());
			userProfessor.setNome(pessoaSession.getNome());			
			userProfessor.setPapel(pessoaSession.getPapel());
			userProfessor.getContato().setEmail(pessoaSession.getContato().getEmail());
			userProfessor.setSenha(pessoaSession.getSenha());
			userProfessor.getEndereco().setCidade(pessoaSession.getEndereco().getCidade());
			userProfessor.setCurso(pessoaSession.getCurso());
		}		
	}
	

	public Professor getUserProfessor() {
		return userProfessor;
	}
	public void setUserProfessor(Professor userProfessor) {
		this.userProfessor = userProfessor;
	}
	

}
