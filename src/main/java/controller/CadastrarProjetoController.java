package controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.dao.ProjetoDAO;
import model.pojo.Projeto;

@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {
		
	private Projeto projeto = null;
	
	private String cursoEnvolvido = null;
	
	
	public CadastrarProjetoController() {
		this.projeto = new Projeto();
	}

	public void salvarProjeto() {
		projeto.setDataPublicacao(new Date());
		new ProjetoDAO().salvar(projeto);
		System.out.println("ENTROU AQUI!!");
	}
	
	public String getCursoEnvolvido() {
		return cursoEnvolvido;
	}

	public void setCursoEnvolvido(String cursoEnvolvido) {
		this.cursoEnvolvido = cursoEnvolvido;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

}
