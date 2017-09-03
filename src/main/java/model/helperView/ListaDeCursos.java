package model.helperView;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Curso;

@ManagedBean(name = "listaDeCursos")
@ViewScoped
public class ListaDeCursos {

	
	/** Inst�ncia da lista que armazena todos os cursos da UNIV�S. */
	private List<Curso> cursos;
	
	/** Lista dos cursos da UNIV�S em tipo String (somente o nome do curso). */
	private List<String> strCursos;
	
	
	public ListaDeCursos() {
		montaListaStringDeCursos();
	}
	
	
	
	/**
	 * Retorna uma lista de objetos String contendo os
	 * nomes de todos os cursos da UNIV�S.
	 */
	public List<String> getStrCursos(){
		return strCursos;
	}
	
	/**
	 * Define uma nova lista de objetos String com os nomes dos cursos da UNIV�S.
	 */
	public void setStrCursos(List<String> cursos) {
		this.strCursos = cursos;
	}
	
	/**
	 * Retorna uma lista de objetos Curso.
	 * Lista de todos os cursos da UNIV�S.
	 */
	public List<Curso> getCursos(){
		return cursos;
	}
	
	/**
	 * Define uma nova lista de objetos Curso.
	 */
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	
	/**
	 * Preenche uma lista contendo todos os cursos da UNIV�S.
	 */
	private void montaListaDeCursos(){
		this.cursos = new ArrayList<Curso>();
		
		cursos.add(new Curso("Administra��o"));
		cursos.add(new Curso("Biologia"));
		cursos.add(new Curso("Contabilidade"));
		cursos.add(new Curso("Engenharia de Produ��o"));
		cursos.add(new Curso("Farm�cia"));
		cursos.add(new Curso("Fisioterapia"));
		cursos.add(new Curso("Gest�o de Produ��o Industrial"));
		cursos.add(new Curso("Hist�ria"));
		cursos.add(new Curso("Psicologia"));
		cursos.add(new Curso("Recursos Humanos"));
		cursos.add(new Curso("Administra��o"));
		cursos.add(new Curso("Administra��o"));
		cursos.add(new Curso("Sistemas de Informa��o"));		
	}

	
	
	/**
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIV�S.
	 */
	private void montaListaStringDeCursos(){
		this.strCursos = new ArrayList<String>();
		
		strCursos.add("Administra��o");
		strCursos.add("Biologia");
		strCursos.add("Contabilidade");
		strCursos.add("Engenharia de Produ��o");
		strCursos.add("Farm�cia");
		strCursos.add("Fisioterapia");
		strCursos.add("Gest�o de Produ��o Industrial");
		strCursos.add("Hist�ria");
		strCursos.add("Psicologia");
		strCursos.add("Recursos Humanos");
		strCursos.add("Administra��o");
		strCursos.add("Administra��o");
		strCursos.add("Sistemas de Informa��o");	
	}
	
}
