package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Projeto;



@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {
		
	
	private Projeto projeto = null;
	
	
	public CadastrarProjetoController() {
		this.projeto = new Projeto();
	}

	
	
	public void salvarProjeto(){
		
		System.out.println("Dados do novo projeto salvos com sucesso!");
		
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

}
