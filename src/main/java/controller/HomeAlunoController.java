package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.ProjetoBean;
import model.dao.AlunoDAO;
import model.pojo.Aluno;
import model.pojo.Habilidade;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import view.Mensagem;
import web.SessionUtil;


@ManagedBean(name = "homeController")
@ViewScoped
public class HomeAlunoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProjetoBean projetoBean;
	
	// Lista com todos os projetos cadastrados no banco de dados.
	private List<Projeto> todosProjetos;
	
	// Consultar projetos que contenham esta palavra no título ou descrição.
	private String palavraChave = null;
	
	// Consulta projetos cadastrados neste grupo.
	private String onde = null;
	
	// Consulta projetos com essa habilidade;
	private String habilidade = null;
	
	// Buscar projetos que contenham esta habilidade.
	private List<String> habilidades = new ArrayList<String>();
	
	// Guarda os valores do usuário que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	
	// Usuário aluno da sessão atual.
	private Aluno userAluno = new Aluno();
	
	// Projeto selecionado na lista de projetos.
	private Projeto projetoSelecionado = new Projeto();
	
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	
	/**
	 * Construtor da classe.
	 */
	public HomeAlunoController() { 
		
		this.pessoaSession = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		if(pessoaSession != null){
			userAluno.setId(pessoaSession.getId());
			userAluno.setNome(pessoaSession.getNome());
			userAluno.setPapel(pessoaSession.getPapel());
			userAluno.getContato().setEmail(pessoaSession.getContato().getEmail());
			userAluno.setSenha(pessoaSession.getSenha());
			userAluno.getEndereco().setCidade(pessoaSession.getEndereco().getCidade());
			userAluno.setCurso(pessoaSession.getCurso());
		}
		this.projetoBean = new ProjetoBean();
		this.todosProjetos = projetoBean.getTodosProjetos();
		carregaListaDeHabilidades(userAluno);
	}
	
	
	
	/**
	 * Carrega a lista de habilidades que será exibida na caixa de combinação
	 * na home do aluno.
	 */
	private void carregaListaDeHabilidades(Aluno al){
		String email = al.getContato().getEmail();
		String senha = al.getSenha();
		List<Habilidade> habilidadesDoAluno = new AlunoDAO().listaHabilidadesDoAluno(email, senha);
		
		for (Habilidade hab : habilidadesDoAluno) {
			this.habilidades.add(hab.getDescricao());
		}
	}
	
	
	
	/**
	 * Método executado quando o usuário preencher os campos da tela inicial
	 * para buscar novos projetos.
	 * 
	 * O método deve usar como filtro de seleção a palavra que o usuário 
	 * digitar no campo de texto e as opções que forem escolhidas nos combobox.
	 */
	public void pesquisarProjetos(){
				
		// Caso o usuário faça uma busca em branco, retorna para ele todos os projetos cadastrados.
		List<Projeto> projetosLocalizados = todosProjetos;
		
		// Busca por palavra chave.
		if(!palavraChave.isEmpty()){
			projetosLocalizados = buscaPorPalavraChave(todosProjetos);
		}
		
		// Busca por habilidade.
		if(!habilidade.equals("Habilidade")){
			if(!palavraChave.isEmpty()){
				projetosLocalizados = buscaPorHabilidade(projetosLocalizados);
			}else{
				projetosLocalizados = buscaPorHabilidade(todosProjetos);
			}
		}
		projetoBean.setTodosProjetos(projetosLocalizados);
	}
	
	
	
	/**
	 * Busca em uma lista os projetos que contenham a palavra chave no título,
	 * descrição ou resumo.
	 * 
	 * A palavra chave é informada pelo usuário no campo de texto na home do aluno.
	 * 
	 * @param projetos Lista utilizada para buscar os projetos.
	 * @return Lista de projetos selecionados.
	 */
	private List<Projeto> buscaPorPalavraChave(List<Projeto> projetos){
		
		List<Projeto> projetosLocalizados = new ArrayList<Projeto>();
		String titulo, descricao, resumo;
		
		// Busca na lista de todos os projetos cadastrados.
		for (Projeto projeto : projetos) {
			
			titulo = projeto.getTitulo().toLowerCase();
			descricao = projeto.getDescricaoCurta().toLowerCase();
			resumo = projeto.getResumo().toLowerCase();

			palavraChave = palavraChave.toLowerCase();

			// Separa o projeto se encontrar a palavra-chave no título, descrição ou resumo.
			if(titulo.contains(palavraChave) || 
			   descricao.contains(palavraChave) ||
			   resumo.contains(palavraChave)){
				
				projetosLocalizados.add(projeto);					
			}
		}
		return projetosLocalizados;
	}
	
	
	
	/**
	 * Busca projetos pela habilidade informada.
	 * 
	 * @param projetos
	 * @return Lista de projetos localizados.
	 */
	private List<Projeto> buscaPorHabilidade(List<Projeto> projetos){
		List<Projeto> projetosLocalizados = new ArrayList<Projeto>();
		
		// Busca na lista de todos os projetos cadastrados.
		for (Projeto projeto : projetos) {
			
			// Lista de habilidades exigidas pelo projeto.
			List<Habilidade> habilidadesExigidas = projeto.getHabilidades();
			
			// Percorre a lista de habilidades buscando uma semelhante a habilidade selecionada.
			for (Habilidade h : habilidadesExigidas) {
				if(h.getDescricao().equals(habilidade)){
					projetosLocalizados.add(projeto);
				}
			}
		}
		return projetosLocalizados;
	}
	
	
	
	
	/**
	 * Recarrega a lista com todos os projetos cadastrados no sistema.
	 */
	public void recarregarProjetos(){
		projetoBean.setTodosProjetos(this.todosProjetos);
	}
	
	
	
	
	/**
	 * Mostra no console qual projeto o usuário deseja ver detalhes. 
	 */
	public void verDetalhes(){
		System.out.println("DETALHES: "+projetoSelecionado.getTitulo()+" - "+projetoSelecionado.getCoordenador().getNome());
	}
	
	
	/**
	 * Executa esse método quando o aluno deseja se candidatar a um projeto.
	 * 
	 * Deve-se montar uma relação de nós entre o projeto escolhido e o aluno logado.
	 * 
	 * O script que faz a ligação entre os nós deve ser construído no AlunoDAO,
	 * algo tipo: participarDeProjeto(Projeto pj).
	 */
	public void candidatarAoProjeto(){
		
		Pessoa usuario = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		String nome = usuario.getNome();
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		String curso = usuario.getCurso().getNome();
		
		Aluno aluno = new Aluno(nome, curso, email, senha);
		
		// Verifica se o aluno já participa do projeto.
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoSelecionado);
				
		if(participa){
			Mensagem.ExibeMensagemAtencao("Você já faz parte desse projeto.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoSelecionado);
			
			if(candidatou) {
				Mensagem.ExibeMensagem("Inscrito com sucesso!");
			}		
			else {
				Mensagem.ExibeMensagemErro("Não foi possível se candidatar a esse projeto.");
			}
		}
	}
	
	
	
	/**
	 * Retorna o objeto da classe ProjetoBean que contém a lista estática de projetos. 
	 */
	public ProjetoBean getProjetoBean(){
		return projetoBean;
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

	public List<String> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<String> habilidades) {
		this.habilidades = habilidades;
	}
	
	public void setProjetoBean(ProjetoBean projetoBean) {
		this.projetoBean = projetoBean;
	}
	public Aluno getUserAluno() {
		return userAluno;
	}
	public void setUserAluno(Aluno userAluno) {
		this.userAluno = userAluno;
	}
	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}
	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
	public String getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(String habilidade) {
		this.habilidade = habilidade;
	}
	
	
}
