package model.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import interfaces.NivelDeConhecimento;


@ManagedBean
public class Habilidade implements NivelDeConhecimento {

	private String descricao;
	private String nivel;
	private List<String> niveisDeConhecimento;
	
	
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
	/**
	 * Retorna uma lista de objetos String contendo os possíveis
	 * níveis de conhecimento de uma habilidade.
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
