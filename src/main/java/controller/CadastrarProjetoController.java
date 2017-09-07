package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Projeto;
import view.Mensagem;

@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {

	// Guarda os dados do novo projeto que ser� cadastrado.
	private Projeto projeto = new Projeto();;

	
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

}
