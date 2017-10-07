package model.pojo;

import java.util.List;

public class Professor extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	private String dataAdmissao="";
	private String titulacao="";
	private List<Projeto> projetos;
	
	
	public Professor() {
		setPapel("Professor");
	}

	
	public Professor(String nome, String curso, String email, String senha) { 
		super(nome, curso, email, senha);
		setPapel("Professor");		
	}
	
	
	public Professor(String nome, String titulacao){
		setNome(nome);
		setTitulacao(titulacao);
	}
	

	public String getDataAdmissao() {
		return dataAdmissao;
	}


	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}


	public String getTitulacao() {
		return titulacao;
	}


	public void setTitulacao(String titulacao) {
		this.titulacao = titulacao;
	}


	public List<Projeto> getProjetos() {
		return projetos;
	}


	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}


	/**
	 * Adiciona um novo projeto àlista de projetos deste professor.
	 */
	public void addProjeto(Projeto novoProjeto){
		novoProjeto.setCoordenador(this);
		getProjetos().add(novoProjeto);	
	}
	
	
}
