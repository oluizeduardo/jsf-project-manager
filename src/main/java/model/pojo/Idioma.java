package model.pojo;

public class Idioma {

	private String nomeIdioma;
	private String nivelDeConhecimento;
	
	public Idioma(String nome, String nivel) {
		this.nomeIdioma = nome;
		this.nivelDeConhecimento = nivel;
	}

	public String getNomeIdioma() {
		return nomeIdioma;
	}

	public void setNomeIdioma(String nomeIdioma) {
		this.nomeIdioma = nomeIdioma;
	}

	public String getNivelDeConhecimento() {
		return nivelDeConhecimento;
	}

	public void setNivelDeConhecimento(String nivelDeConhecimento) {
		this.nivelDeConhecimento = nivelDeConhecimento;
	}
	
	
	
	
}
