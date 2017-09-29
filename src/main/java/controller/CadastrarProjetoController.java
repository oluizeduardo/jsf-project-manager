package controller;

import java.io.Serializable;
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

@ManagedBean
@ViewScoped
public class CadastrarProjetoController implements Serializable{

	private static final long serialVersionUID = 1L;

	// Guarda os dados do novo projeto que será cadastrado.
	private Projeto projeto = new Projeto();
	// Lista de habilidades exigidas neste projeto.
	private List<Habilidade> habilidades = new ArrayList<Habilidade>();
	
	// Habilidade selecionada para ser excluida da lista.
	private Habilidade habilidade = new Habilidade();
	
	// Habilidade selecionada para ser excluída da lista.
	private Habilidade habilidadeSelecionada = null;
	// Acesso à lista de cursos aos quais o novo projeto se destina.
	private List<String> cursosAlvo = new ArrayList<String>();	
	// Curso alvo do projeto.
	private String cursoAlvo = null;
	// Curso alvo do projeto.
	private String cursoSelecionado = null;
	
	
	
	public CadastrarProjetoController() { }

	
	
	
	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	private String getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	
	/**
	 * Envia os dados de um novo projeto para o banco de dados.
	 * É exibido uma mensagem na tela informando o usuário sobre o status da execução.
	 */
	public void salvarProjeto() {
				
		if(projeto.getTitulo().isEmpty()){
			Mensagem.ExibeMensagemAtencao("Informe o título do projeto.");
		}else{
			if(projeto.getDataInicio().isEmpty() || projeto.getDataFim().isEmpty()){
				Mensagem.ExibeMensagemAtencao("Informe corretamente a data de início e fim do projeto.");
			}else{
				if(projeto.getDescricaoCurta().isEmpty()){
					Mensagem.ExibeMensagemAtencao("Descreva este projeto em até 70 caractéres.");
				}else{
					
					projeto.setDataPublicacao(getDataAtual());
					boolean salvou = new ProjetoDAO().salvar(projeto);
					
					if(salvou){
						Mensagem.ExibeMensagem("Novo projeto salvo com sucesso!");
						
						// Reinicia o objeto para limpar os campos da tela.
						this.projeto = new Projeto();
					}else{
						Mensagem.ExibeMensagemErro("Houve um problema ao tentar salvar o novo projeto.");
					}
				}
			}
		}
	}

	
	/**
	 * Retorna uma lista de todos os projetos cadastrados no banco de dados.
	 */
	public ArrayList<Projeto> buscarProjeto() {
		return (ArrayList<Projeto>) (new ProjetoDAO().listar());
	}

	
	/**
	 * Adiciona uma nova habilidade na lista de habilidades exigidas pelo projeto.
	 */
	public void addHabilidade(){
		String desc = habilidade.getDescricao().toUpperCase();
		String nivel = habilidade.getNivel();
		
		// Verifica se a descrição não está em branco.
		if(!desc.isEmpty())
			if(!verificaExistenciaDeHabilidade(desc))
				this.habilidades.add(new Habilidade(desc, nivel));
	}
	
	
	/**
	 * Verifica se na lista de habilidades já não existe a habilidade
	 * que se deseja cadastrar.
	 * 
	 * @param descricao A descrição da nova habilidade
	 * @return verdadeiro ou falso sobre a existência da nova habilidade.
	 */
	private boolean verificaExistenciaDeHabilidade(String descricao){		
		for (Habilidade habilidade : habilidades) {
			if(habilidade.getDescricao().equals(descricao)){
				return true;
			}
		}		
		return false;
	}
	
	
	/**
	 * Exclui uma habilidade da lista de habilidades exigidas no projeto.
	 */
	public void excluiHabilidade(){
		if(habilidadeSelecionada != null)
			this.habilidades.remove(habilidadeSelecionada);
	}
	
	
	/**
	 * Adiciona um novo curso na lista dos cursos alvo do novo projeto.
	 */
	public void addCursoAlvo(){
		if(cursoAlvo != null)
			if(!cursosAlvo.contains(cursoAlvo))
				this.cursosAlvo.add(cursoAlvo);
	}
	
	
	/**
	 * Exclui um curso da lista de cursos alvo do projeto.
	 */
	public void excluiCursoAlvo(){
		if(cursoSelecionado != null)
			cursosAlvo.remove(cursoSelecionado);
	}


	
	
	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
	public Habilidade getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}
	public String getCursoAlvo() {
		return cursoAlvo;
	}
	public void setCursoAlvo(String cursoAlvo) {
		this.cursoAlvo = cursoAlvo;
	}
	public List<String> getCursosAlvo() {
		return cursosAlvo;
	}
	public void setCursosAlvo(List<String> cursosAlvo) {
		this.cursosAlvo = cursosAlvo;
	}
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public String getCursoSelecionado() {
		return cursoSelecionado;
	}
	public void setCursoSelecionado(String cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	public Habilidade getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}
	public void setHabilidadeSelecionada(Habilidade habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}
	public List<Habilidade> getHabilidades() {
		return habilidades;
	}
	
}
