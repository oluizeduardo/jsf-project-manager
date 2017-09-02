package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import model.pojo.Projeto;

@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {
	
	private Projeto projeto = null;
	
	public CadastrarProjetoController() {
		this.projeto = new Projeto();
	}
	
	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}
	
	public void salvar(){
		
		System.out.println("Dados do professor salvos com sucesso!");
		
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	@Override
	public String toString() {
		return "CadastrarProjetoController [projeto=" + projeto + "]";
	}
	
	

}
