package model;

public class Idioma {

	private String nomeIdioma;
	private String nivelDeHabilidade;
	
	public Idioma(String nome, String nivel) {
		this.nomeIdioma = nome;
		this.nivelDeHabilidade = nivel;
	}

	public String getNomeIdioma() {
		return nomeIdioma;
	}

	public void setNomeIdioma(String nomeIdioma) {
		this.nomeIdioma = nomeIdioma;
	}

	public String getNivelDeHabilidade() {
		return nivelDeHabilidade;
	}

	public void setNivelDeHabilidade(String nivelDeHabilidade) {
		this.nivelDeHabilidade = nivelDeHabilidade;
	}
	
	
	
	
}
