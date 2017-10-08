package model.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProjetoRecomendado extends Projeto {

	// Informa em uma única String as habilidades em comum com um projeto.
	private List<Habilidade> habilidadesEmComum = null;
	// Todas as habilidades da lista de habilidades comuns em uma única String.
	private String str_habilidadesComuns="";
	
	
	public ProjetoRecomendado() { 
		this.habilidadesEmComum = new ArrayList<Habilidade>();
	}


	public List<Habilidade> getHabilidadesEmComum() {
		return habilidadesEmComum;
	}


	public void setHabilidadesEmComum(List<Habilidade> habilidadesEmComum) {
		this.habilidadesEmComum = habilidadesEmComum;
	}
	
	public void addHabilidadeEmComum(Habilidade habilidade){
		this.habilidadesEmComum.add(habilidade);
				
		if(habilidadesEmComum.size() > 1)
			this.str_habilidadesComuns += ", "+habilidade.getDescricao();
		else
			this.str_habilidadesComuns = habilidade.getDescricao();
	}


	public String getStr_habilidadesComuns() {
		return str_habilidadesComuns;
	}
	public void setStr_habilidadesComuns(String str_habilidadesComuns) {
		this.str_habilidadesComuns = str_habilidadesComuns;
	}
	
	
}
