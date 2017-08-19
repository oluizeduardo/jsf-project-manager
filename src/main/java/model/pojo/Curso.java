package model.pojo;

import java.util.List;

public class Curso {

	private long codigo;
	private String nome;
	private List<Disciplina> disciplinas;
	private List<Aluno> alunos;
	private Professor coordenador;
	
	
	public Curso() {}
	
	
	
	public Curso(String nome) {
		this.nome = nome;
	}


	
	
	public long getCodigo() {
		return codigo;
	}


	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}


	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}


	public List<Aluno> getAlunos() {
		return alunos;
	}


	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}


	public Professor getCoordenador() {
		return coordenador;
	}


	public void setCoordenador(Professor coordenador) {
		this.coordenador = coordenador;
	}
	
}
