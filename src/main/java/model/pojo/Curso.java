package model.pojo;


public class Curso {

	private long codigo;
	private String nome;
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

	public Professor getCoordenador() {
		return coordenador;
	}


	public void setCoordenador(Professor coordenador) {
		this.coordenador = coordenador;
	}
	
}
