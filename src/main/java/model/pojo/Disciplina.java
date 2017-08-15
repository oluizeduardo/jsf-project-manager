package model.pojo;



public class Disciplina {

	
	private int codigo;
	private String descricao;
	private Professor professorTitular;
	
	
	public Disciplina() { }


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Professor getProfessorTitular() {
		return professorTitular;
	}


	public void setProfessorTitular(Professor professorTitular) {
		this.professorTitular = professorTitular;
	}

}
