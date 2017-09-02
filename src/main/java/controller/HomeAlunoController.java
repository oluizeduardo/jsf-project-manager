package controller;

import javax.faces.bean.ManagedBean;

import model.helperView.ProjetoBean;

@ManagedBean(name = "homeController")
public class HomeAlunoController {
	
	// Lista provis�ria at� a aplica��o estar conectada ao banco de dados.
	private ProjetoBean projetoBean;
	
	// Consultar projetos que contenham esta palavra no t�tulo ou descri��o.
	private String palavraChave;
	
	// Consulta projetos cadastrados neste grupo.
	private String onde;
	
	// Buscar projetos que contenham esta habilidade.
	private String habilidade;
	
	
	
	
	
	public HomeAlunoController() { 
		this.projetoBean = new ProjetoBean();
	}
	
	
	/**
	 * M�todo executado quando o usu�rio preencher os campos da tela inicial
	 * para buscar novos projetos.
	 * 
	 * O m�todo deve usar como filtro de sele��o a palavra que o usu�rio 
	 * digitar no campo de texto e as op��es que forem escolhidas nos combobox.
	 */
	public void pesquisarProjetos(){
		System.out.println("Pesquisando projetos... ("+palavraChave+", "+onde+", "+habilidade+")");
	}
	
	
	/**
	 * Retorna o objeto da classe ProjetoBean que cont�m a lista est�tica de projetos. 
	 */
	public ProjetoBean getProjetoBean(){
		return projetoBean;
	}

	
	/**
	 * Retorna o valor da porcentagem de campos preenchidos do perfil.
	 */
	public int getProgressoDeAtualizacaoPerfil(){
		return 30;
	}



	public String getPalavraChave() {
		return palavraChave;
	}



	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}



	public String getOnde() {
		return onde;
	}



	public void setOnde(String onde) {
		this.onde = onde;
	}



	public String getHabilidade() {
		return habilidade;
	}



	public void setHabilidade(String habilidade) {
		this.habilidade = habilidade;
	}



	public void setProjetoBean(ProjetoBean projetoBean) {
		this.projetoBean = projetoBean;
	}
	
	
}
