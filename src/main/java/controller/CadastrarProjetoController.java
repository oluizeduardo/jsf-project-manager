package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Habilidade;
import model.pojo.Projeto;
import view.Mensagem;

@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {

	// Guarda os dados do novo projeto que ser� cadastrado.
	private Projeto projeto = new Projeto();
	
	// Lista de habilidades exigidas neste projeto.
	private List<Habilidade> habilidades;
	
	// Habilidade selecionada para ser excluida da lista.
	private Habilidade habSelecionada = new Habilidade(null, null);
	
	// Data m�nima para o in�cio do projeto.
	private Date dataMinima = new Date();
	
	
	
	public CadastrarProjetoController() { }

	
	/**
	 * Retorna a data atual formatada para o padr�o de leitura simples.
	 */
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	
	/**
	 * Envia os dados de um novo projeto para o banco de dados.
	 * � exibido uma mensagem na tela informando o usu�rio sobre o status da execu��o.
	 */
	public void salvarProjeto() {
		projeto.setDataPublicacao(getCurrentDate());
		
		//TODO
//		projeto.setDataFim(getCurrentDate());
//		projeto.setDataInicio(getCurrentDate());
		
		boolean salvou = new ProjetoDAO().salvar(projeto);
		
		if(salvou){
			Mensagem.ExibeMensagem("Novo projeto salvo com sucesso!");
		}else{
			Mensagem.ExibeMensagemErro("Houve um problema ao tentar salvar o novo projeto.");
		}
	}

	
	/**
	 * Retorna uma lista de todos os projetos cadastrados no banco de dados.
	 */
	public ArrayList<Projeto> buscarProjeto() {
		return (ArrayList<Projeto>) (new ProjetoDAO().listar());
	}


	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Habilidade> getHabilidades() {
		if(habilidades == null){
			habilidades = new ArrayList<Habilidade>();
			habilidades.add(new Habilidade("Java", "M�dio"));
			habilidades.add(new Habilidade("Banco de dados", "B�sico"));
			habilidades.add(new Habilidade("Javascript", "Avan�ado"));
			habilidades.add(new Habilidade("Engenharia de Software", "B�sico"));
			habilidades.add(new Habilidade("Python", "B�sico"));
		}		
		return habilidades;
	}

	public void exluirHabilidade(){
		this.habilidades.remove(habSelecionada);
		System.out.println("REMOVIDO: "+habSelecionada.getDescricao());
	}
	
	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
	public Habilidade getHabSelecionada() {
		return habSelecionada;
	}
	public void setHabSelecionada(Habilidade habSelecionada) {
		this.habSelecionada = habSelecionada;
	}
	public Date getDataMinima() {
		return dataMinima;
	}
	public void setDataMinima(Date dataMinima) {
		this.dataMinima = dataMinima;
	}

}
