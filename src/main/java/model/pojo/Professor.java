package model.pojo;

import java.util.List;

public class Professor extends Pessoa {

	private String dataAdmissao;
	private String cargo;
	private String titulacao;
	private List<Projeto> projetos;
	
	
	public Professor() { }

	
	public Professor(String nome, String titulacao) { 
		super.setNome(nome);
		this.titulacao = titulacao;
	}
	

	public String getDataAdmissao() {
		return dataAdmissao;
	}


	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
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
