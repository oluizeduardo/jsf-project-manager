package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import web.SessionUtil;

@ManagedBean
@ViewScoped
public class BuscarAlunosController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Todos alunos cadastrados.
	private List<Aluno> alunos;
	// Lista de alunos indicados para um professor.
	private List<Aluno> alunosIndicados = new ArrayList<Aluno>();
	// Objeto com métodos de acesso ao banco de dados.
	private AlunoDAO alunoDAO = null;
	
	// Diz se a lista de alunos cadastrados está vazia.
	private boolean alunosVazio = true;
	// Diz se a lista de alunos indicados está vazia.
	private boolean alunosIndicadosVazio = true;
	// Aluno selecionado na tabela.
	private Aluno alunoSelecionado = new Aluno();
	// Controla a aparição do painel com mais detalhes sobre o aluno.
	private boolean maisDetalhesVisivel = false;
	
	
	
	
	public BuscarAlunosController() { 	
		this.alunoDAO = new AlunoDAO();
		this.alunos = alunoDAO.listar();
		atualizaListaDeIndicados();
	}


	
	public void visualizarMaisDetalhes(){
		if(this.maisDetalhesVisivel){
			this.maisDetalhesVisivel = false;
		}			
		else{
			this.maisDetalhesVisivel = true;
		}
	}
	
	
	/**
	 * Preenche a lista de alunos indicados para o projeto.
	 */
	public void atualizaListaDeIndicados(){
		// Retorna os dados do professor logado.
		Pessoa donoDoProjeto = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		// Retorna uma lista de alunos indicados para trabalharem com o professor.
		this.alunosIndicados = alunoDAO.getAlunosIndicadosParaProfessor(donoDoProjeto);
	}
	
	
	
	/**
	 * Diz se a lista de alunos cadastrados está vazia.
	 */
	public boolean isAlunosVazio(){
		this.alunosVazio = (this.alunos.size() < 1);
		return alunosVazio;
	}
	
	/**
	 * Diz se a lista de alunos indicados para os projetos está vazia.
	 */
	public boolean isAlunosIndicadosVazio(){
		this.alunosIndicadosVazio = (this.alunosIndicados.size() < 1);
		return alunosIndicadosVazio;
	}
	
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	public List<Aluno> getAlunosIndicados() {
		return alunosIndicados;
	}
	public void setAlunosIndicados(List<Aluno> alunosIndicados) {
		this.alunosIndicados = alunosIndicados;
	}
	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}
	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}
	public boolean isMaisDetalhesVisivel() {
		return maisDetalhesVisivel;
	}
	public void setMaisDetalhesVisivel(boolean maisDetalhesVisivel) {
		this.maisDetalhesVisivel = maisDetalhesVisivel;		
	}
	
	
	
}
