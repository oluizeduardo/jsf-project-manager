package model.pojo;

public class Habilidade {

	private String descricao;
	private String nivel;
	
	
	public Habilidade() {}
	
	public Habilidade(String desc, String nivel) {
		this.descricao = desc;
		this.nivel = nivel;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	
	
	
}
