package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Projeto;
import view.Mensagem;


@ManagedBean
public class EditarProjetoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Projeto selecionado para edição.
	private Projeto projeto = new Projeto();
	// Curso para o qual o projeto editado se destina.
	private String cursoAlvo = null;
	// Lista de cursos para os quais o projeto editado se destina.
	private List<Curso> cursosAlvo = new ArrayList<Curso>();
	// Curso selecionado para ser excluído.
	private Curso cursoSelecionado = new Curso();
	// Nova habilidade.
	private Habilidade habilidade = new Habilidade();
	
	
	public EditarProjetoController() { 
		System.out.println("Salvando as edições do projeto "+projeto.getTitulo());
	}

	
	
	/**
	 * Invoca o método responsável por atualizar os dados do projeto
	 * no banco de dados.
	 */
	public void salvarEdicao(){
		System.out.println("Salvando as edições do projeto "+projeto.getTitulo());
		Mensagem.ExibeMensagem("Alterações salvas com sucesso!!");
	}
	
	
	
	/**
	 * Adiciona um novo curso na lista de cursos alvo.
	 */
	public void addHabilidade(){
		System.out.println("Adicinoando nova habilidade: "+habilidade.getDescricao());
	}
	
	/**
	 * Adiciona um novo curso na lista de cursos alvo.
	 */
	public void addCursoAlvo(){
		System.out.println("Adicinoando novo curso alvo: "+cursoAlvo);
	}
	
	/**
	 * Adiciona um novo curso na lista de cursos alvo.
	 */
	public void excluiCursoAlvo(){
		System.out.println("Adicinoando novo curso alvo: "+cursoAlvo);
	}
	
	
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public String getCursoAlvo() {
		return cursoAlvo;
	}
	public void setCursoAlvo(String cursoAlvo) {
		this.cursoAlvo = cursoAlvo;
	}
	public List<Curso> getCursosAlvo() {
		return cursosAlvo;
	}
	public void setCursosAlvo(List<Curso> cursosAlvo) {
		this.cursosAlvo = cursosAlvo;
	}
	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}
	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	public Habilidade getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}
	
	
	
	
}
