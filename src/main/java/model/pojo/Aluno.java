package model.pojo;

import java.util.List;


public class Aluno extends Usuario {

	
	private String curso;	
	private List<Habilidade> habilidades;
	private String dataMatricula;
	
	
	
	
	public Aluno() { }


	
	
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
