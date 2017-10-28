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
import model.pojo.ProjetoComDetalhesComum;
import view.Mensagem;
import web.SessionUtil;


@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// Lista com todos os projetos cadastrados.
	private List<Projeto> todosProjetos = null;
	// Lista com todos os projetos disponíveis.
	private List<Projeto> todosProjetosDisponiveis = null;
	// Lista de projetos que o aluno participa.
	private List<Projeto> projetosQueParticipo = null;
	// Lista de projetos recomendados ao aluno.
	private List<ProjetoComDetalhesComum> projetosRecomendados = null;
	// Projeto com detalhes. Usado na tabela de projetos sugeridos ao aluno.
	private ProjetoComDetalhesComum projetoRecomendadoSelecionado = new ProjetoComDetalhesComum();
	// Projeto normal, sem detalhes.
	private Projeto projetoNormalSelecionado = new Projeto();
	
	// Diz se a lista de projetos recomendados está vazia.
	private boolean recomendadosVazio = true; 
	// Diz se a lista com todos os projetos está vazia.
	private boolean todosProjetosVazio = true;
	// Diz se a lista de projetos que participo está vazia.
	private boolean projetosQueParticipoVazio = true;
	
	// Diz se o projeto recomendado está disponível para novas candidaturas.
	private boolean projetoRecomendadoDisponivel = true;
	// Diz se o projeto está disponível para novas candidaturas.
	private boolean projetoNormalDisponivel = true;
	
	
	
	public ProjetoBean() {
		
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = pessoa.getContato().getEmail();
		String senha = pessoa.getSenha();
		
		AlunoDAO alunoDAO = new AlunoDAO();		
		Aluno aluno = alunoDAO.buscarAluno(email, senha);

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
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoNormalSelecionado);
		
		// Verifica se o aluno já manifestou intresse pelo projeto.
		boolean jaTemInteresse = alunoDAO.verificaInteresseEmProjeto(aluno, projetoNormalSelecionado);
				
		if(participa){
			Mensagem.ExibeMensagemAtencao("Você já faz parte desse projeto.");			
		
		}else if(jaTemInteresse){
			Mensagem.ExibeMensagemAtencao("Você já manifestou interesse por esse projeto. Aguarde aprovação.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoNormalSelecionado);
			
			if(candidatou) {
				Mensagem.ExibeMensagem("Solicitação enviada ao coordenador do projeto. Aguardando aprovação.");
			}		
			else {
				Mensagem.ExibeMensagemErro("Não foi possível se candidatar a esse projeto.");
			}
		}
	}
	

	
	
	
	/**
	 * Executa esse método quando o aluno deseja se candidatar a um projeto
	 * recomendado a ele.
	 * 
	 * Deve-se montar uma relação de nós entre o projeto escolhido e o aluno logado.
	 * 
	 * O script que faz a ligação entre os nós deve ser construído no AlunoDAO,
	 * algo tipo: participarDeProjeto(Projeto pj).
	 */
	public void candidatarAoProjetoRecomendado(){
		
		Pessoa usuario = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		AlunoDAO alunoDAO = new AlunoDAO();
		
		String nome = usuario.getNome();
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		String curso = usuario.getCurso().getNome();
		
		Aluno aluno = new Aluno(nome, curso, email, senha);
		
		// Verifica se o aluno já participa do projeto.
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoRecomendadoSelecionado);
		
		// Verifica se o aluno já manifestou intresse pelo projeto.
		boolean jaTemInteresse = alunoDAO.verificaInteresseEmProjeto(aluno, projetoRecomendadoSelecionado);
		
		if(participa){
			Mensagem.ExibeMensagemAtencao("Você já faz parte desse projeto.");			
		
		}else if(jaTemInteresse){
			Mensagem.ExibeMensagemAtencao("Você já manifestou interesse por esse projeto. Aguarde aprovação.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoRecomendadoSelecionado);
			
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
		this.projetosQueParticipoVazio = (projetosQueParticipo.isEmpty());
		return projetosQueParticipoVazio;
	}
	
	/**
	 * Diz se a lista com todos os projetos está vazia ou não.
	 */
	public boolean isTodosProjetosVazio() {
		this.todosProjetosVazio = (todosProjetos.isEmpty());
		return todosProjetosVazio;
	}
	
	/**
	 * Diz se a lista de projetos recomendados está vazia ou não.
	 */
	public boolean isRecomendadosVazio() {
		this.recomendadosVazio = (projetosRecomendados.size() < 1);
		return recomendadosVazio;
	}

	/**
	 * Retorna uma lista com todos os projetos cadastrados e que tem o status
	 * diferente de 'Finalizado'.
	 */
	public List<Projeto> getTodosProjetosDisponiveis() {
		if(todosProjetosDisponiveis == null){
			this.todosProjetosDisponiveis = new ProjetoDAO().listarProjetosDisponiveis();
		}
		return todosProjetosDisponiveis;
	}	
	public void setTodosProjetosDisponiveis(List<Projeto> todosProjetosDisponiveis) {		
		this.todosProjetosDisponiveis = todosProjetosDisponiveis;
	}
	public List<Projeto> getProjetosQueParticipo() {
		return projetosQueParticipo;
	}
	public List<ProjetoComDetalhesComum> getProjetosRecomendados() {
		return projetosRecomendados;
	}
	public void setProjetosRecomendados(List<ProjetoComDetalhesComum> projetosRecomendados) {
		this.projetosRecomendados = projetosRecomendados;
	}
	public ProjetoComDetalhesComum getProjetoRecomendadoSelecionado() {
		return projetoRecomendadoSelecionado;
	}
	public void setProjetoRecomendadoSelecionado(ProjetoComDetalhesComum projetoSelecionado) {
		this.projetoRecomendadoSelecionado = projetoSelecionado;
	}
	public Projeto getProjetoNormalSelecionado() {
		return projetoNormalSelecionado;
	}
	public void setProjetoNormalSelecionado(Projeto projetoNormalSelecionado) {
		this.projetoNormalSelecionado = projetoNormalSelecionado;
	}
	/**
	 * Verifica se o projeto recomendado selecionado não está finalizado.
	 * O aluno não pode se candidatar a um projeto que já está finalizado.
	 */
	public boolean isProjetoRecomendadoDisponivel() {
		this.projetoRecomendadoDisponivel = (projetoRecomendadoSelecionado.getStatus().equals(Projeto.FINALIZADO));
		return projetoRecomendadoDisponivel;
	}
	/**
	 * Verifica se o projeto selecionado não está finalizado.
	 * O aluno não pode se candidatar a um projeto que já está finalizado.
	 */
	public boolean isProjetoNormalDisponivel() {
		this.projetoNormalDisponivel = (projetoNormalSelecionado.getStatus().equals(Projeto.FINALIZADO));
		return projetoNormalDisponivel;
	}
	/**
	 * Retorna uma lista com todos os projetos cadastrados no banco de dados.
	 */
	public List<Projeto> getTodosProjetos() {
		if(todosProjetos == null){
			this.todosProjetos = new ProjetoDAO().listar();
		}
		return todosProjetos;
	}
	public void setTodosProjetos(List<Projeto> todosProjetos) {
		this.todosProjetos = todosProjetos;
	}
	
}
