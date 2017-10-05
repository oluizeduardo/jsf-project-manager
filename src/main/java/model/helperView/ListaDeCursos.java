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

	
	/** Instância da lista que armazena todos os cursos da UNIVÁS. */
	private List<SelectItem> cursos;
	
	
	public ListaDeCursos() {
		montaListaDeCursos();
	}
	
	
	
	
	/**
	 * Retorna uma lista de objetos String.
	 * Lista de todos os cursos da UNIVÁS.
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
	 * Preenche uma lista de objetos String contendo todos os cursos da UNIVÁS.
	 */
	private void montaListaDeCursos(){
		
		SelectItemGroup g1 = new SelectItemGroup("Cursos UNIVÁS");
        g1.setSelectItems(new SelectItem[] {
        		new SelectItem("Administração", "Administração"), 
        		new SelectItem("Ciências Biológicas", "Ciências Biológicas"), 
        		new SelectItem("Contabilidade", "Contabilidade"),
        		new SelectItem("Educação Física", "Educação Física"),
        		new SelectItem("Engenharia de Produção", "Engenharia de Produção"), 
        		new SelectItem("Enfermagem", "Enfermagem"),
        		new SelectItem("Farmácia", "Farmácia"),
        		new SelectItem("Fisioterapia", "Fisioterapia"), 
        		new SelectItem("Gastronomia", "Gastronomia"),
        		new SelectItem("Educação Física", "Educação Física"),
        		new SelectItem("Gestão de Produção Industrial", "Gestão de Produção Industrial"), 
        		new SelectItem("Gestão de Recursos Humanos", "Gestão de Recursos Humanos"), 
        		new SelectItem("História", "História"),
        		new SelectItem("Medicina", "Medicina"),
        		new SelectItem("Nutrição", "Nutrição"),
        		new SelectItem("Pedagogia", "Pedagogia"), 
        		new SelectItem("Psicologia", "Psicologia"), 
        		new SelectItem("Publicidade", "Publicidade"),
        		new SelectItem("Sistemas de Informação", "Sistemas de Informação")});      
         
        cursos = new ArrayList<SelectItem>();
        cursos.add(g1);
		
		
//		cursos.add("Administração");
//		cursos.add("Ciências Biológicas");
//		cursos.add("Contabilidade");
//		cursos.add("Educação Física");
//		cursos.add("Engenharia de Produção");
//		cursos.add("Enfermagem");
//		cursos.add("Farmácia");
//		cursos.add("Fisioterapia");
//		cursos.add("Gastronomia");
//		cursos.add("Gestão de Produção Industrial");
//		cursos.add("Gestão de Recursos Humanos");
//		cursos.add("História");
//		cursos.add("Medicina");	
//		cursos.add("Nutrição");
//		cursos.add("Pedagogia");
//		cursos.add("Psicologia");
//		cursos.add("Publicidade");			
//		cursos.add("Sistemas de Informação");		
	}

}
