package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.ProjetoBean;
import model.dao.AlunoDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import view.Mensagem;
import web.SessionUtil;


@ManagedBean(name = "homeController")
@ViewScoped
public class HomeAlunoController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista provisória até a aplicação estar conectada ao banco de dados.
	private ProjetoBean projetoBean;
	
	// Consultar projetos que contenham esta palavra no título ou descrição.
	private String palavraChave;
	
	// Consulta projetos cadastrados neste grupo.
	private String onde;
	
	// Buscar projetos que contenham esta habilidade.
	private String habilidade;
	
	// Guarda os valores do usuário que acabou de logar no sistema.
	private Pessoa pessoaSession = null;
	
	// Usuário aluno da sessão atual.
	private Aluno userAluno = new Aluno();
	
	// Projeto selecionado na lista de projetos.
	private Projeto projetoSelecionado = new Projeto();
	
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	
	public HomeAlunoController() { 
		this.projetoBean = new ProjetoBean();
		
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
		
	}
	
	
	/**
	 * Método executado quando o usuário preencher os campos da tela inicial
	 * para buscar novos projetos.
	 * 
	 * O método deve usar como filtro de seleção a palavra que o usuário 
	 * digitar no campo de texto e as opções que forem escolhidas nos combobox.
	 */
	public void pesquisarProjetos(){
		System.out.println("Pesquisando projetos... ("+palavraChave+", "+onde+", "+habilidade+")");
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
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		String email = pessoa.getContato().getEmail();
		String password = pessoa.getSenha();
		String nomeProjeto = projetoSelecionado.getTitulo();
		
		System.out.println("nome do proojeto:"+nomeProjeto);
		
		boolean candidatar = alunoDAO.canditarProjeto(email, password, nomeProjeto);
		
		if(candidatar) {
			Mensagem.ExibeMensagem("Candidatura realizada com sucesso.");
		}
		
		else {
			Mensagem.ExibeMensagem("Não foi possível realizar a candidatura a esse projeto.");
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

	public String getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(String habilidade) {
		this.habilidade = habilidade;
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
	
	
}
