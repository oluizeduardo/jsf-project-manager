package model.pojo;

import java.util.List;

public class Professor extends Usuario {

	private String dataAdmissao;
	private String profissao;
	private String titulacao;
	private List<Disciplina> disciplinas;
	
	
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


	public String getProfissao() {
		return profissao;
	}


	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}


	public String getTitulacao() {
		return titulacao;
	}


	public void setTitulacao(String titulacao) {
		this.titulacao = titulacao;
	}


	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}


	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	
	public void addDisciplina(Disciplina novaDisciplina){
		novaDisciplina.setProfessorTitular(this);
		getDisciplinas().add(novaDisciplina);	
	}
	
	
}
