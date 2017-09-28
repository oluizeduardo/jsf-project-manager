package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Projeto;

@ManagedBean(name = "projetosAlunoController")
@ViewScoped
public class BuscarProjetosAlunoController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Projeto selecionado para ver detalhes.
	private Projeto projetoSelecionado = null;
	
	
	

	public BuscarProjetosAlunoController() { }

	
	
	
	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}
	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
}
