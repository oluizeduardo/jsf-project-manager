package model.helperView;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Curso;

@ManagedBean(name = "listaDeCursos")
@ViewScoped
public class ListaDeCursos {

	
	/** Instância da lista que armazena todos os cursos da UNIVÁS. */
	private List<Curso> cursos;
	
	/** Lista dos cursos da UNIVÁS em tipo String (somente o nome do curso). */
	private List<String> strCursos;
	
	
	public ListaDeCursos() {
		montaListaStringDeCursos();
	}
	
	
	
	/**
	 * Retorna uma lista de objetos String contendo os
	 * nomes de todos os cursos da UNIVÁS.
	 */
	public List<String> getStrCursos(){
		return strCursos;
	}
	
	/**
	 * Define uma nova lista de objetos String com os nomes dos cursos da UNIVÁS.
	 */
	public void setStrCursos(List<String> cursos) {
		this.strCursos = cursos;
	}
	
	/**
	 * Retorna uma lista de objetos Curso.
	 * Lista de todos os cursos da UNIVÁS.
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
	 * Preenche uma lista contendo todos os cursos da UNIVÁS.
	 */
	private void montaListaDeCursos(){
		this.cursos = new ArrayList<Curso>();
		
		cursos.add(new Curso("Administração"));
		cursos.add(new Curso("Biologia"));
		cursos.add(new Curso("Contabilidade"));
		cursos.add(new Curso("Engenharia de Produção"));
		cursos.add(new Curso("Farmácia"));
		cursos.add(new Curso("Fisioterapia"));
		cursos.add(new Curso("Gestão de Produção Industrial"));
		cursos.add(new Curso("História"));
		cursos.add(new Curso("Psicologia"));
		cursos.add(new Curso("Recursos Humanos"));
		cursos.add(new Curso("Administração"));
		cursos.add(new Curso("Administração"));
		cursos.add(new Curso("Sistemas de Informação"));		
	}

	
	
	/**
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIVÁS.
	 */
	private void montaListaStringDeCursos(){
		this.strCursos = new ArrayList<String>();
		
		strCursos.add("Administração");
		strCursos.add("Biologia");
		strCursos.add("Contabilidade");
		strCursos.add("Engenharia de Produção");
		strCursos.add("Farmácia");
		strCursos.add("Fisioterapia");
		strCursos.add("Gestão de Produção Industrial");
		strCursos.add("História");
		strCursos.add("Psicologia");
		strCursos.add("Recursos Humanos");
		strCursos.add("Administração");
		strCursos.add("Administração");
		strCursos.add("Sistemas de Informação");	
	}
	
}
