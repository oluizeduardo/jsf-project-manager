package model.helperView;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;


@ManagedBean(name = "listaDeCursos")
@ViewScoped
public class ListaDeCursos {

	
	/** Inst�ncia da lista que armazena todos os cursos da UNIV�S. */
	private List<SelectItem> cursos;
	
	
	public ListaDeCursos() {
		montaListaDeCursos();
	}
	
	
	
	
	/**
	 * Retorna uma lista de objetos String.
	 * Lista de todos os cursos da UNIV�S.
	 */
	public List<SelectItem> getCursos(){
		return cursos;
	}
	
	/**
	 * Define uma nova lista de cursos.
	 */
	public void setCursos(List<SelectItem> cursos) {
		this.cursos = cursos;
	}
	
	
	/**
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIV�S.
	 */
	private void montaListaDeCursos(){
		
		SelectItemGroup g1 = new SelectItemGroup("Cursos UNIV�S");
        g1.setSelectItems(new SelectItem[] {
        		new SelectItem("Administra��o", "Administra��o"), 
        		new SelectItem("Ci�ncias Biol�gicas", "Ci�ncias Biol�gicas"), 
        		new SelectItem("Contabilidade", "Contabilidade"),
        		new SelectItem("Educa��o F�sica", "Educa��o F�sica"),
        		new SelectItem("Engenharia de Produ��o", "Engenharia de Produ��o"), 
        		new SelectItem("Enfermagem", "Enfermagem"),
        		new SelectItem("Farm�cia", "Farm�cia"),
        		new SelectItem("Fisioterapia", "Fisioterapia"), 
        		new SelectItem("Gastronomia", "Gastronomia"),
        		new SelectItem("Educa��o F�sica", "Educa��o F�sica"),
        		new SelectItem("Gest�o de Produ��o Industrial", "Gest�o de Produ��o Industrial"), 
        		new SelectItem("Gest�o de Recursos Humanos", "Gest�o de Recursos Humanos"), 
        		new SelectItem("Hist�ria", "Hist�ria"),
        		new SelectItem("Medicina", "Medicina"),
        		new SelectItem("Nutri��o", "Nutri��o"),
        		new SelectItem("Pedagogia", "Pedagogia"), 
        		new SelectItem("Psicologia", "Psicologia"), 
        		new SelectItem("Publicidade", "Publicidade"),
        		new SelectItem("Sistemas de Informa��o", "Sistemas de Informa��o")});      
         
        cursos = new ArrayList<SelectItem>();
        cursos.add(g1);
		
		
//		cursos.add("Administra��o");
//		cursos.add("Ci�ncias Biol�gicas");
//		cursos.add("Contabilidade");
//		cursos.add("Educa��o F�sica");
//		cursos.add("Engenharia de Produ��o");
//		cursos.add("Enfermagem");
//		cursos.add("Farm�cia");
//		cursos.add("Fisioterapia");
//		cursos.add("Gastronomia");
//		cursos.add("Gest�o de Produ��o Industrial");
//		cursos.add("Gest�o de Recursos Humanos");
//		cursos.add("Hist�ria");
//		cursos.add("Medicina");	
//		cursos.add("Nutri��o");
//		cursos.add("Pedagogia");
//		cursos.add("Psicologia");
//		cursos.add("Publicidade");			
//		cursos.add("Sistemas de Informa��o");		
	}

}
