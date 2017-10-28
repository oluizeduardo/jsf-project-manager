package model.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import interfaces.NivelDeConhecimento;

@ManagedBean
public class Idioma implements NivelDeConhecimento {

	private String nomeIdioma;
	private String nivelDeConhecimento;
	private List<String> niveisDeConhecimento;
	
	
	public Idioma() { }
	
	
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
	
	/**
	 * Retorna uma lista de objetos String contendo os possíveis
	 * níveis de conhecimento em um idioma.
	 */
	public List<String> getNiveisDeConhecimento() {
		if(niveisDeConhecimento == null){
			niveisDeConhecimento = new ArrayList<String>();
			niveisDeConhecimento.add(NivelDeConhecimento.BASICO);
			niveisDeConhecimento.add(NivelDeConhecimento.MEDIO);
			niveisDeConhecimento.add(NivelDeConhecimento.AVANCADO);
		}
		return niveisDeConhecimento;
	}
	public void setNiveisDeConhecimento(List<String> niveisDeConhecimento) {
		this.niveisDeConhecimento = niveisDeConhecimento;
	}
	
	
	
}
