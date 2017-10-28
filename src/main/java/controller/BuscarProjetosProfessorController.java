package controller;


import java.io.Serializable;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import model.dao.ProjetoDAO;
import model.pojo.Pessoa;
import model.pojo.Professor;
import model.pojo.Projeto;
import view.Mensagem;
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
	
	// Diz se a lista de projetos cadastrados está vazia.
	private boolean projetosVazio = true;
	
	// O projeto selecionado na tabela.
	private Projeto projetoSelecionado = new Projeto();
	
	// Diz se um determinado projeto é de um outro autor.
	// Variável necessária para bloquear os botões de Editar e Excluir.
	private boolean projetoDeOutroProfessor = false;
	
	
	
	public BuscarProjetosProfessorController() {
		listaProjetosCadastradosPeloProfessor();
	}
	
	
	

	/**
	 * Exclui do banco de dados o projeto selecionado.
	 */
	public void excluirProjeto(){
		boolean excluiu = new ProjetoDAO().excluir(projetoSelecionado);
		if(excluiu){
			projetos.remove(projetoSelecionado);
			recarregaPagina();
			Mensagem.ExibeMensagem("Projeto excluído com sucesso!");
		}else{
			Mensagem.ExibeMensagemErro("Erro ao excluir projeto.");
		}
	}
	
	
	
//	/**
//	 * Altera o status de um determinado projeto para cancelado.
//	 */
//	public void cancelarProjeto(){
//		boolean cancelou = new ProjetoDAO().cancelar(projetoSelecionado);
//		if(cancelou){
//			recarregaPagina();
//			Mensagem.ExibeMensagem("Projeto cancelado com sucesso!");
//		}
//	}
	
	
	/**
	 * Método para recarregar a página.
	 */
	public void recarregaPagina() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
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


	
	/**
	 * Diz se a lista de projetos cadastrados está vazia ou não.
	 */
	public boolean isProjetosVazio(){
		this.projetosVazio = (projetos.size() < 1);
		return projetosVazio;
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
	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}
	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
	/**
	 * Verifica o nome, email e senha. Compara as propriedades do projeto selecionado
	 * com as propriedades da pessoa logada no sistema para verificar se o projeto
	 * selecionado pertence realmente à pessoa online.
	 */
	public boolean isProjetoDeOutroProfessor() {
		Pessoa pessoaLogada = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String nomeProfLogado = pessoaLogada.getNome();
		String emailProfLogado = pessoaLogada.getContato().getEmail();
		String senhaProfLogado = pessoaLogada.getSenha();
		
		Pessoa profProjetoSelecionado = projetoSelecionado.getCoordenador();
		
		if(!nomeProfLogado.equals(profProjetoSelecionado.getNome())){
			if(!emailProfLogado.equals(profProjetoSelecionado.getContato().getEmail())){
				if(!senhaProfLogado.equals(profProjetoSelecionado.getSenha())){
					projetoDeOutroProfessor = true;
				}else{
					projetoDeOutroProfessor = false;
				}
			}else{
				projetoDeOutroProfessor = false;
			}
		}else{
			projetoDeOutroProfessor = false;
		}		
		return projetoDeOutroProfessor;
	}
	
}
