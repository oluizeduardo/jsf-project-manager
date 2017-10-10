package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.dao.ProjetoDAO;
import model.pojo.Notificacao;
import model.pojo.Pessoa;
import model.pojo.Professor;
import web.SessionUtil;

@ManagedBean(name = "homeProfessorController")
@ViewScoped
public class HomeProfessorController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Guarda os valores do usu�rio que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	// Usu�rio professor da sess�o atual.
	private Professor userProfessor;
	// Lista de notifica��es.
	private List<Notificacao> notificacoes = null;
	// Quantidade de notifica��es.
	private int qtdeNotificacoes = 0;
	
	

	public HomeProfessorController() {
		// Recupera os dados da Session para apresenta��o no painel de perfil.
		this.pessoaSession = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoaSession != null) {
			userProfessor = new Professor();
			userProfessor.setId(pessoaSession.getId());
			userProfessor.setNome(pessoaSession.getNome());
			userProfessor.setPapel(pessoaSession.getPapel());
			userProfessor.getContato().setEmail(pessoaSession.getContato().getEmail());
			userProfessor.setSenha(pessoaSession.getSenha());
			userProfessor.getEndereco().setCidade(pessoaSession.getEndereco().getCidade());
			userProfessor.setCurso(pessoaSession.getCurso());
		}
		carregaListaDeNotificacoes();
	}

	
	/**
	 * Carrega a lista de notifica��es e atualiza a quantidade
	 * de notifica��es que ser� exibida no bot�o na barra de menu 
	 * do professor.
	 */
	private void carregaListaDeNotificacoes(){
		this.notificacoes = new ProjetoDAO().getNotificacoesDeInteresseEmProjetos(userProfessor);
		this.qtdeNotificacoes = notificacoes.size();
	}
	
	
	public Professor getUserProfessor() {
		return userProfessor;
	}

	public void setUserProfessor(Professor userProfessor) {
		this.userProfessor = userProfessor;
	}
	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}
	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}
	public int getQtdeNotificacoes() {
		return qtdeNotificacoes;
	}
	public void setQtdeNotificacoes(int qtdeNotificacoes) {
		this.qtdeNotificacoes = qtdeNotificacoes;
	}
}
