package model.helperView;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "listaDeCursos")
@ViewScoped
public class ListaDeCursos {

	
	/** Instância da lista que armazena todos os cursos da UNIVÁS. */
	private List<String> cursos;
	
	
	public ListaDeCursos() {
		montaListaDeCursos();
	}
	
	
	
	
	/**
	 * Retorna uma lista de objetos String.
	 * Lista de todos os cursos da UNIVÁS.
	 */
	public List<String> getCursos(){
		return cursos;
	}
	
	/**
	 * Define uma nova lista de cursos.
	 */
	public void setCursos(List<String> cursos) {
		this.cursos = cursos;
	}
	
	
	/**
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIVÁS.
	 */
	private void montaListaDeCursos(){
		this.cursos = new ArrayList<String>();
		
		cursos.add("Administração");
		cursos.add("Ciências Biológicas");
		cursos.add("Contabilidade");
		cursos.add("Educação Física");
		cursos.add("Engenharia de Produção");
		cursos.add("Enfermagem");
		cursos.add("Farmácia");
		cursos.add("Fisioterapia");
		cursos.add("Gastronomia");
		cursos.add("Gestão de Produção Industrial");
		cursos.add("Gestão de Recursos Humanos");
		cursos.add("História");
		cursos.add("Medicina");	
		cursos.add("Nutrição");
		cursos.add("Pedagogia");
		cursos.add("Psicologia");
		cursos.add("Publicidade");			
		cursos.add("Sistemas de Informação");		
	}

}
