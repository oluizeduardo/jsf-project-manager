package model.pojo;

public class ProjetosPublicadosPorCurso {

	private String curso = null;
	private int mes = 0;
	private int quantidade = 0;
	
	
	
	public ProjetosPublicadosPorCurso() {	}
	
	
	public ProjetosPublicadosPorCurso(String nomeCurso) {
		this.curso = nomeCurso;
	}


	
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getCurso() {
		return curso;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getMes() {
		return mes;
	}	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}