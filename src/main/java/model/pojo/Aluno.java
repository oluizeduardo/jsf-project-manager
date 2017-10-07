package model.pojo;

import java.util.List;

public class Aluno extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	
	private List<Habilidade> habilidades;
	private List<Idioma> idiomas;
	private Projeto projetoIndicado = new Projeto();
	
	
	public Aluno() { 
		setPapel("Aluno");
	}
	
	
	public Aluno(String nome, String curso, String email, String senha) { 
		super(nome, curso, email, senha);
		setPapel("Aluno");
	}	

	public List<Idioma> getIdiomas() {
		return idiomas;
	}


	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
	public void addHabilidade(Habilidade novaHabilidade){
		getHabilidades().add(novaHabilidade);
	}
	public Projeto getProjetoIndicado() {
		return projetoIndicado;
	}
	public void setProjetoIndicado(Projeto projetoIndicado) {
		this.projetoIndicado = projetoIndicado;
	}
	
}

