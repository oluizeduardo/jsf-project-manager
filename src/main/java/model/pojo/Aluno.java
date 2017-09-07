package model.pojo;

import java.util.List;


public class Aluno extends Pessoa {
	
	private String curso;	
	private List<Habilidade> habilidades;
	private List<Idioma> idiomas;
	private List<Projeto> projetos;
	private String dataMatricula;
	
	
	public Aluno() { 
		setPapel("Aluno");
	}
	
	
	public Aluno(String nome, String email, String senha) { 
		super(nome, email, senha);
	}	
	
	
	
	
	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<Idioma> getIdiomas() {
		return idiomas;
	}


	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}


	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public String getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(String dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

	public void addHabilidade(Habilidade novaHabilidade){
		getHabilidades().add(novaHabilidade);
	}
	
}

