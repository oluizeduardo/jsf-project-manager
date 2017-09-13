package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import model.pojo.Pessoa;
import model.pojo.Professor;
import model.pojo.Projeto;
import web.SessionUtil;



@ManagedBean(name = "homeProfessorController")
@SessionScoped
public class HomeProfessorController {
		
	// Instância com os dados do novo projeto cadastrado pelo professor.
	private Projeto novoProjeto = new Projeto();
	
	// Acesso à lista de cursos aos quais o novo projeto se destina.
	private List<String> cursosAlvo = new ArrayList<String>();	
	
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
		}
		
	}
	
	

	/**
	 * Adiciona um novo curso na lista dos cursos alvo do novo projeto.
	 */
	public void adicionaNovoCursoAlvo(ActionEvent ev){
		System.out.println("Adiciona um novo curso na lista dos cursos alvo do novo projeto.");
	}

	/**
	 * Retorna a lista de cursos alvo para o novo projeto.
	 */
	public List<String> getCursosAlvo() {
		return cursosAlvo;
	}

	/**
	 * Define uma nova lista de cursos alvo para o novo projeto.
	 */
	public void setCursosAlvo(List<String> cursosAlvo) {
		this.cursosAlvo = cursosAlvo;
	}
	
	/**
	 * Retorna a instância de umm novo objeto Projeto a ser cadastrado.
	 */
	public Projeto getNovoProjeto() {
		return novoProjeto;
	}

	/**
	 * Define uma nova instância para o objeto Projeto. 
	 */
	public void setNovoProjeto(Projeto novoProjeto) {
		this.novoProjeto = novoProjeto;
	}

	public Professor getUserProfessor() {
		return userProfessor;
	}

	public void setUserProfessor(Professor userProfessor) {
		this.userProfessor = userProfessor;
	}

}
