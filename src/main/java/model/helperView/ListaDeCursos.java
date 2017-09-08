package model.helperView;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "listaDeCursos")
@ViewScoped
public class ListaDeCursos {

	
	/** Inst�ncia da lista que armazena todos os cursos da UNIV�S. */
	private List<String> cursos;
	
	
	public ListaDeCursos() {
		montaListaDeCursos();
	}
	
	
	
	
	/**
	 * Retorna uma lista de objetos String.
	 * Lista de todos os cursos da UNIV�S.
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
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIV�S.
	 */
	private void montaListaDeCursos(){
		this.cursos = new ArrayList<String>();
		
		cursos.add("Administra��o");
		cursos.add("Ci�ncias Biol�gicas");
		cursos.add("Contabilidade");
		cursos.add("Educa��o F�sica");
		cursos.add("Engenharia de Produ��o");
		cursos.add("Enfermagem");
		cursos.add("Farm�cia");
		cursos.add("Fisioterapia");
		cursos.add("Gastronomia");
		cursos.add("Gest�o de Produ��o Industrial");
		cursos.add("Gest�o de Recursos Humanos");
		cursos.add("Hist�ria");
		cursos.add("Medicina");	
		cursos.add("Nutri��o");
		cursos.add("Pedagogia");
		cursos.add("Psicologia");
		cursos.add("Publicidade");			
		cursos.add("Sistemas de Informa��o");		
	}

}
