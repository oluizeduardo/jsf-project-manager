package controller;


import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Pessoa;
import model.pojo.Professor;
import model.pojo.Projeto;
import web.SessionUtil;

@ManagedBean(name = "projetosProfController")
@ViewScoped
public class BuscarProjetosProfessorController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String TODOS_PROJETOS = "Todos os projetos cadastrados";
	private static final String MEUS_PROJETOS = "Cadastrados por mim";
	
	
	// A opção que o professor escolheu para buscar projetos.
	private String tipoDeBusca = null;

	/**Lista de projetos exibida na tabela.*/
	private List<Projeto> projetos;
	
	
	
	public BuscarProjetosProfessorController() {
		listaProjetosCadastradosPeloProfessor();
	}
	
	
	
	/**
	 * Altera os objetos da lista de projetos baseado no tipo de busca 
	 * que o professor deseja fazer.
	 */
	public void alteraListaDeProjetos(){
		if(tipoDeBusca.equals(TODOS_PROJETOS)){
			this.projetos = new ProjetoDAO().listar();
			
		}else if(tipoDeBusca.equals(MEUS_PROJETOS)){
			listaProjetosCadastradosPeloProfessor();
		}
	}


	/**
	 * Lista todos os projetos cadastrados pelo professor logado no sistema.
	 */
	private void listaProjetosCadastradosPeloProfessor() {
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		Professor professor = new Professor();
		professor.getContato().setEmail(pessoa.getContato().getEmail());
		professor.setSenha(pessoa.getSenha());
		
		this.projetos = new ProjetoDAO().listarPorProfessor(professor);		
	}



	public String getTipoDeBusca() {
		return tipoDeBusca;
	}
	public void setTipoDeBusca(String tipoDeBusca) {
		this.tipoDeBusca = tipoDeBusca;
	}
	public List<Projeto> getProjetos() {
		return projetos;
	}
	public void setTodosProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
	
}
