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

	// Guarda os dados do novo projeto que será cadastrado.
	private Projeto projeto = new Projeto();
	
	// Lista de habilidades exigidas neste projeto.
	private List<Habilidade> habilidades;
	
	// Habilidade selecionada para ser excluida da lista.
	private Habilidade habSelecionada = new Habilidade(null, null);
	
	// Data mínima para o início do projeto.
	private Date dataMinima = new Date();
	
	
	
	public CadastrarProjetoController() { }

	
	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	
	/**
	 * Envia os dados de um novo projeto para o banco de dados.
	 * É exibido uma mensagem na tela informando o usuário sobre o status da execução.
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
			habilidades.add(new Habilidade("Java", "Médio"));
			habilidades.add(new Habilidade("Banco de dados", "Básico"));
			habilidades.add(new Habilidade("Javascript", "Avançado"));
			habilidades.add(new Habilidade("Engenharia de Software", "Básico"));
			habilidades.add(new Habilidade("Python", "Básico"));
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
