package controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import view.Mensagem;
import web.SessionUtil;


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
				
				String dataInicio = projeto.getDataInicio();
				String dataFim = projeto.getDataFim();
				
				if(!validaDatas(dataInicio, dataFim)){
					Mensagem.ExibeMensagemAtencao("Escreva corretamente as datas de inicio e fim do projeto.");
				}else{
					if(projeto.getDescricaoCurta().isEmpty()){
						Mensagem.ExibeMensagemAtencao("Descreva este projeto em até 70 caractéres.");
					}else{
						
						// Retorna os dados do professor logado.
						Pessoa professorLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
						projeto.setDataPublicacao(getDataAtual());
						projeto.setHabilidades(habilidades);
						projeto.setCursosEnvolvidos(geraListaDeCursosEnvolvidos());
						projeto.setCoordenador(professorLogado);
						
						boolean salvou = new ProjetoDAO().salvar(projeto);
						
						if(salvou){
							Mensagem.ExibeMensagem("Novo projeto salvo com sucesso!");
							
							// Reinicia o objeto para limpar os campos da tela.
							this.projeto = new Projeto();
							reiniciaListas();
						}else{
							Mensagem.ExibeMensagemErro("Houve um problema ao tentar salvar o novo projeto.");
						}
					}
				}
			}
		}
	}

	
	/**
	 * Remove todos os registros das listas utilizadas no cadastro de novo projeto.
	 */
	private void reiniciaListas(){
		habilidades = new ArrayList<Habilidade>();
		cursosAlvo = new ArrayList<String>();
	}
	
	
	/**
	 * O método realiza uma adaptação da lista de String para uma lista de objetos Curso.
	 * @return Uma lista de Cursos.
	 */
	private List<Curso> geraListaDeCursosEnvolvidos(){
		List<Curso> cursosEnvolvidos = new ArrayList<Curso>();
		for (String curso : cursosAlvo) {
			cursosEnvolvidos.add(new Curso(curso));
		}
		return cursosEnvolvidos;
	}
	
	
	
	/**
	 * Valida as datas de inicio e fim do projeto.
	 */
	private boolean validaDatas(String dataInicio, String dataFim){
		String str_dataInicio = new String(dataInicio);
		String str_dataFim = new String(dataFim);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
	     	format.setLenient(false);
	     	format.parse(str_dataInicio);
	     	format.parse(str_dataFim);
	     	
	     	return true;
		} catch (ParseException e) {
		    return false;
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
	public void addHabilidade() {
		String desc = habilidade.getDescricao().toUpperCase();
		String nivel = habilidade.getNivel();

		// Verifica se a descrição não está em branco.
		if (!desc.isEmpty()){
			// Verifica se ainda não existe tal habilidade no banco de dados.
			if (!verificaHabilidadeNaLista(desc)){
				Habilidade novaHabilidade = new Habilidade(desc, nivel);				
				this.habilidades.add(novaHabilidade);
			}
		}
	}
	
	
	/**
	 * Verifica se na lista de habilidades já não existe a habilidade
	 * que se deseja cadastrar.
	 * 
	 * @param descricao A descrição da nova habilidade
	 * @return verdadeiro ou falso sobre a existência da nova habilidade.
	 */
	private boolean verificaHabilidadeNaLista(String descricao){		
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
