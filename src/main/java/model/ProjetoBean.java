package model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.dao.ProjetoDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import model.pojo.ProjetoRecomendado;
import view.Mensagem;
import web.SessionUtil;


@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Projeto> todosProjetos = null;
	private List<Projeto> projetosQueParticipo = null;
	private List<ProjetoRecomendado> projetosRecomendados = null;
	private Projeto projetoSelecionado = new ProjetoRecomendado();
	
	// Diz se a lista de projetos recomendados está vazia.
	private boolean recomendadosVazio = true; 
	// Diz se a lista com todos os projetos está vazia.
	private boolean todosProjetosVazio = true;
	// Diz se a lista de projetos que participo está vazia.
	private boolean projetosQueParticipoVazio = true;
	
	
	
	public ProjetoBean() {
		
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = pessoa.getContato().getEmail();
		String senha = pessoa.getSenha();
		
		AlunoDAO alunoDAO = new AlunoDAO();
		
		Aluno aluno = alunoDAO.buscarAluno(email, senha);
		this.todosProjetos = new ProjetoDAO().listar();
		this.projetosQueParticipo = alunoDAO.getProjetosQueParticipa(aluno);
		this.projetosRecomendados = alunoDAO.getProjetosRecomendados(aluno);
	}

	
	/**
	 * Executa esse método quando o aluno deseja se candidatar a um projeto.
	 * 
	 * Deve-se montar uma relação de nós entre o projeto escolhido e o aluno logado.
	 * 
	 * O script que faz a ligação entre os nós deve ser construído no AlunoDAO,
	 * algo tipo: participarDeProjeto(Projeto pj).
	 */
	public void candidatarAoProjeto(){
		
		Pessoa usuario = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		AlunoDAO alunoDAO = new AlunoDAO();
		
		String nome = usuario.getNome();
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		String curso = usuario.getCurso().getNome();
		
		Aluno aluno = new Aluno(nome, curso, email, senha);
		
		// Verifica se o aluno já participa do projeto.
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoSelecionado);
		
		// Verifica se o aluno já manifestou intresse pelo projeto.
		boolean jaTemInteresse = alunoDAO.verificaInteresseEmProjeto(aluno, projetoSelecionado);
		
		if(participa){
			Mensagem.ExibeMensagemAtencao("Você já faz parte desse projeto.");			
		
		}else if(jaTemInteresse){
			Mensagem.ExibeMensagemAtencao("Você já manifestou interesse por esse projeto. Aguarde aprovação.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoSelecionado);
			
			if(candidatou) {
				Mensagem.ExibeMensagem("Solicitação enviada ao coordenador do projeto. Aguardando aprovação.");
			}		
			else {
				Mensagem.ExibeMensagemErro("Não foi possível se candidatar a esse projeto.");
			}
		}
	}
	
	
	/**
	 * Diz se a lista com os projetos que participo está vazia ou não.
	 */
	public boolean isProjetosQueParticipoVazio() {
		this.projetosQueParticipoVazio = (projetosQueParticipo.size() < 1);
		return projetosQueParticipoVazio;
	}
	
	/**
	 * Diz se a lista com todos os projetos está vazia ou não.
	 */
	public boolean isTodosProjetosVazio() {
		this.todosProjetosVazio = (todosProjetos.size() < 1);
		return todosProjetosVazio;
	}
	
	/**
	 * Diz se a lista de projetos recomendados está vazia ou não.
	 */
	public boolean isRecomendadosVazio() {
		this.recomendadosVazio = (projetosRecomendados.size() < 1);
		return recomendadosVazio;
	}


	public List<Projeto> getTodosProjetos() {		
		return todosProjetos;
	}	
	public void setTodosProjetos(List<Projeto> todosProjetos) {		
		this.todosProjetos = todosProjetos;
	}
	public List<Projeto> getProjetosQueParticipo() {
		return projetosQueParticipo;
	}
	public List<ProjetoRecomendado> getProjetosRecomendados() {
		return projetosRecomendados;
	}
	public void setProjetosRecomendados(List<ProjetoRecomendado> projetosRecomendados) {
		this.projetosRecomendados = projetosRecomendados;
	}
	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}
	public void setProjetoSelecionado(ProjetoRecomendado projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
	
}
