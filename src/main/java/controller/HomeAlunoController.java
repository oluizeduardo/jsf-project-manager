package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.ProjetoBean;
import model.dao.AlunoDAO;
import model.dao.ProjetoDAO;
import model.pojo.Aluno;
import model.pojo.Financiamento;
import model.pojo.Habilidade;
import model.pojo.Notificacao;
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
	
	// Consultar projetos que contenham esta palavra no t�tulo ou descri��o.
	private String palavraChave = null;
	
	// Consulta projetos cadastrados neste grupo.
	private String onde = null;
	
	// Consulta projetos com essa habilidade;
	private String habilidade = null;
	
	// Buscar projetos que contenham esta habilidade.
	private List<String> habilidades = new ArrayList<String>();
	
	// Guarda os valores do usu�rio que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	
	// Usu�rio aluno da sess�o atual.
	private Aluno userAluno = new Aluno();
	
	// Projeto selecionado na lista de projetos.
	private Projeto projetoSelecionado = new Projeto();
	
	// Objeto de acesso aos dados.
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	// Lista de notifica��es do aluno.
	private List<Notificacao> notificacoes = null;
	
	// Notifica��o selecionada para ser exclu�da.
	private Notificacao notificacaoSelecionada = new Notificacao();
	
	// N�mero total de notifica��es n�o lidas pelo aluno.
	private int qtdeNotificacoes =0;
	
	// Diz se o projeto cadastrado possui financiamento ou n�o.
	private String financiamento_ProjetoCadastrado = Financiamento.NAO_POSSUI;
	
	
	
	
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
		this.todosProjetos = projetoBean.getTodosProjetosDisponiveis();
		carregaListaDeHabilidades(userAluno);
		carregaListaDeNotificacoes();
	}
	
	
	
	/**
	 * Remove uma determinada notifica��o da lista de notifica��es.
	 * Atualiza a vari�vel com a quantidade de notifica��es.
	 */
	public void removeNotificacao(){
		if(notificacaoSelecionada != null){
			int projetoID = notificacaoSelecionada.getProjetoID();
			new ProjetoDAO().atualizaMensagemDeParticipacaoLida(userAluno, projetoID);
			this.notificacoes.remove(notificacaoSelecionada);
			this.qtdeNotificacoes = notificacoes.size();
		}
	}
	
	
	
	/**
	 * Carrega a lista de notifica��es e atualiza a quantidade
	 * de notifica��es que ser� exibida no bot�o na barra de menu 
	 * do aluno.
	 */
	private void carregaListaDeNotificacoes(){
		this.notificacoes = new ProjetoDAO().getNotificacoesDeParticipacaoEmProjetos(userAluno);
		this.qtdeNotificacoes = notificacoes.size();
	}
	

	
	/**
	 * Carrega a lista de habilidades que ser� exibida na caixa de combina��o
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
	 * M�todo executado quando o usu�rio preencher os campos da tela inicial
	 * para buscar novos projetos.
	 * 
	 * O m�todo verifica qual(is) o(s) filtro(s) que o usu�rio deseja utilizar
	 * e adapta o script para a busca de projetos no banco de dados.
	 */
	public void pesquisarProjetos(){		
		
		ProjetoDAO projetoDAO = new ProjetoDAO();
		String script = "MATCH (pr:Professor)-[:COORDENA]->(pj:Projeto)";
		String cursoDoAluno = userAluno.getCurso().getNome();
				
		if (onde.equals(cursoDoAluno)) {
			script += "-[:DESTINADO_A]->(c:Curso{nome: '" + cursoDoAluno + "'})";
		}

		if(!habilidade.equals("Habilidade")){
			script += ", (pj)-[:EXIGE]->(h:Habilidade{nome:'"+habilidade+"'})";
		}
		
		if (!palavraChave.isEmpty()) {
			script += " WHERE toLower(pj.titulo) CONTAINS toLower('" + palavraChave +"') "
					+ "OR toLower(pj.descricaoCurta) CONTAINS toLower('" + palavraChave+ "') "
					+ "OR toLower(pj.resumo) CONTAINS toLower('" + palavraChave + "') "
					+ "AND NOT(pj.status='"+Projeto.FINALIZADO+"')";
		}else{
			script += " WHERE NOT(pj.status='"+Projeto.FINALIZADO+"')";
		}

		script += " RETURN pj.titulo as Titulo, ID(pj) as ID, "
				+ "pj.dataFim as Data_Fim, pj.dataInicio as Data_Inicio, "
				+ "pj.dataPublicacao as Publicacao, "
				+ "toFloat(pj.valor) as Valor, "
				+ "pj.natFinanciamento as NatFinanciamento, "
				+ "pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, "
				+ "pj.eFinanciado as eFinanciado, "
				+ "toInteger(pj.numeroParticipantes) as QTD_Participantes, "
				+ "pj.resumo as Resumo, pr.nome as Coordenador";

		List<Projeto> projetosLocalizados = projetoDAO.buscaProjetos(script);
		projetoBean.setTodosProjetosDisponiveis(projetosLocalizados);	
	}
	

	
	/**
	 * Recarrega a lista com todos os projetos cadastrados no sistema.
	 */
	public void recarregarProjetos(){
		projetoBean.setTodosProjetosDisponiveis(this.todosProjetos);
	}
	
	
	
	
	/**
	 * Mostra no console qual projeto o usu�rio deseja ver detalhes. 
	 */
	public void verDetalhes(){
		System.out.println("DETALHES: "+projetoSelecionado.getTitulo()+" - "+projetoSelecionado.getCoordenador().getNome());
	}
	
	
	/**
	 * Executa esse m�todo quando o aluno deseja se candidatar a um projeto.
	 * 
	 * Deve-se montar uma rela��o de n�s entre o projeto escolhido e o aluno logado.
	 * 
	 * O script que faz a liga��o entre os n�s deve ser constru�do no AlunoDAO,
	 * algo tipo: participarDeProjeto(Projeto pj).
	 */
	public void candidatarAoProjeto(){
		
		Pessoa usuario = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		String nome = usuario.getNome();
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		String curso = usuario.getCurso().getNome();
		
		Aluno aluno = new Aluno(nome, curso, email, senha);
		
		// Verifica se o aluno j� participa do projeto.
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoSelecionado);
		
		// Verifica se o aluno j� manifestou intresse pelo projeto.
		boolean jaTemInteresse = alunoDAO.verificaInteresseEmProjeto(aluno, projetoSelecionado);
		
		if(participa){
			Mensagem.ExibeMensagemAtencao("Voc� j� faz parte desse projeto.");			
		
		}else if(jaTemInteresse){
			Mensagem.ExibeMensagemAtencao("Voc� j� manifestou interesse por esse projeto. Aguarde aprova��o.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoSelecionado);
			
			if(candidatou) {
				Mensagem.ExibeMensagem("Solicita��o enviada ao coordenador do projeto. Aguardando aprova��o.");
			}		
			else {
				Mensagem.ExibeMensagemErro("N�o foi poss�vel se candidatar a esse projeto.");
			}
		}
	}
	
	
	
	/**
	 * Retorna o objeto da classe ProjetoBean que cont�m a lista est�tica de projetos. 
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
	public int getQtdeNotificacoes() {
		return qtdeNotificacoes;
	}
	public void setQtdeNotificacoes(int qtdeNotificacoes) {
		this.qtdeNotificacoes = qtdeNotificacoes;
	}
	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}
	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}
	public Notificacao getNotificacaoSelecionada() {
		return notificacaoSelecionada;
	}
	public void setNotificacaoSelecionada(Notificacao notificacaoSelecionada) {
		this.notificacaoSelecionada = notificacaoSelecionada;
	}
	public String getFinanciamento_ProjetoCadastrado() {
		if(projetoSelecionado.getFinanciamento().isExistente())
			this.financiamento_ProjetoCadastrado = Financiamento.POSSUI;
		else
			this.financiamento_ProjetoCadastrado = Financiamento.NAO_POSSUI;
		return financiamento_ProjetoCadastrado;
	}
	public void setFinanciamento_ProjetoCadastrado(String financiamento_ProjetoCadastrado) {
		this.financiamento_ProjetoCadastrado = financiamento_ProjetoCadastrado;
	}
	
}
