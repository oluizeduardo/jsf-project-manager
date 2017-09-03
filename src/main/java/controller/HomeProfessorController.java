package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;



@ManagedBean(name = "homeProfessorController")
@ViewScoped
public class HomeProfessorController {
		
	
	private List<String> cursosAlvo = new ArrayList<String>();	
	
	
	public HomeProfessorController() {  }
	
	

	/**
	 * Adiciona um novo curso na lista dos cursos alvo do novo projeto.
	 */
	public void adicionaNovoCursoAlvo(ActionEvent ev){
		System.out.println("Adiciona um novo curso na lista dos cursos alvo do novo projeto.");
	}

	/**
	 * Retorna a lista de cursos alvo para o novo projeto.
	 */
	public List<String> getCursosAlvo() {
		return cursosAlvo;
	}

	/**
	 * Define uma nova lista de cursos alvo para o novo projeto.
	 */
	public void setCursosAlvo(List<String> cursosAlvo) {
		this.cursosAlvo = cursosAlvo;
	}
	


	/**
	 * Retorna o valor da porcentagem de campos preenchidos do perfil.
	 */
	public int getProgressoDeAtualizacaoPerfil(){
		return 22;
	}

	
	
	
	
}
