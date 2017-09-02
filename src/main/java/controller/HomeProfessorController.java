package controller;

import javax.faces.bean.ManagedBean;



@ManagedBean(name = "homeProfessorController")
public class HomeProfessorController {
		
	
	
	
	public HomeProfessorController() {  }
	
	


	
	/**
	 * Retorna o valor da porcentagem de campos preenchidos do perfil.
	 */
	public int getProgressoDeAtualizacaoPerfil(){
		return 10;
	}

	
}
