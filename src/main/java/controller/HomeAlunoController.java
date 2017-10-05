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
	
	// Consultar projetos que contenham esta palavra no t�tulo ou descri��o.
	private String palavraChave = "";
	
	// Consulta projetos cadastrados neste grupo.
	private String onde = "";
	
	// Consulta projetos com essa habilidade;
	private String habilidade = "";
	
	// Buscar projetos que contenham esta habilidade.
	private List<String> habilidades = new ArrayList<String>();
	
	// Guarda os valores do usu�rio que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	
	// Usu�rio aluno da sess�o atual.
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
	 * O m�todo deve usar como filtro de sele��o a palavra que o usu�rio 
	 * digitar no campo de texto e as op��es que forem escolhidas nos combobox.
	 */
	public void pesquisarProjetos(){
		
		// Verifica se existe chave para buscar projetos.
		// Necess�rio para n�o fazer buscas desnecess�rias no banco de dados.
		if((!palavraChave.equals("")) || (!onde.equals("Onde")) || (!habilidade.equals("Habilidade"))){
			
			// Lista de projetos localizados a partir da busca do aluno.
			List<Projeto> projetosLocalizados = new ArrayList<Projeto>();
			
			// Busca na lista de todos os projetos cadastrados.
			for (Projeto projeto : todosProjetos) {
				
				// Se a palavra chave n�o estiver vazia.
				if(!palavraChave.isEmpty()){
					String titulo = projeto.getTitulo().toLowerCase();
					String descricao = projeto.getDescricaoCurta().toLowerCase();
					String resumo = projeto.getResumo().toLowerCase();

					palavraChave = palavraChave.toLowerCase();

					// Separa o projeto se encontrar a palavra-chave no t�tulo, descri��o ou resumo.
					if(titulo.contains(palavraChave) || 
					   descricao.contains(palavraChave) ||
					   resumo.contains(palavraChave)){
						
						projetosLocalizados.add(projeto);					
					}
				}else{
					// Busca por habilidade selecionada.
					if(!habilidade.equals("Habilidade")){						
						
						// Lista de habilidades exigidas pelo projeto.
						List<Habilidade> habilidadesExigidas = projeto.getHabilidades();
						
						// Percorre a lista de habilidades buscando uma semelhante a habilidade selecionada.
						for (Habilidade h : habilidadesExigidas) {
							if(h.getDescricao().equals(habilidade)){
								projetosLocalizados.add(projeto);
							}
						}
					}
				}
			}
			projetoBean.setTodosProjetos(projetosLocalizados);
		}
	}
	
	
	
	/**
	 * Recarrega a lista com todos os projetos cadastrados no sistema.
	 */
	public void recarregarProjetos(){
		projetoBean.setTodosProjetos(this.todosProjetos);
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
		Aluno aluno = new Aluno(usuario.getNome(), usuario.getContato().getEmail(), usuario.getSenha());
		
		// Verifica se o aluno j� participa do projeto.
		boolean participa = alunoDAO.verificaParticipacaoEmProjeto(aluno, projetoSelecionado);
		
		System.out.println("PARTICIPA DO PROJETO: "+participa);
		
		if(participa){
			Mensagem.ExibeMensagemAtencao("Voc� j� faz parte desse projeto.");			
		}else{
			boolean candidatou = alunoDAO.candidatar(aluno, projetoSelecionado);
			
			if(candidatou) {
				System.out.println("SUCESSO!!!");
				Mensagem.ExibeMensagem("Inscrito com sucesso!");
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
	
	
}
