package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Aluno;
import model.pojo.Habilidade;
import model.pojo.Pessoa;
import view.Mensagem;
import web.SessionUtil;
import model.dao.AlunoDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;
import model.helperView.ListaDeIdiomas;



@ManagedBean(name = "cadastrarAlunoController")
@ViewScoped
public class CadastrarAlunoController {

	// Inst�ncia do aluno logado no sistema.
	private Aluno aluno = null;
	// Acesso � lista de estados brasileiros.
	private List<String> estadosBrasileiros = null;
	// Acesso � lista de estado c�vil.
	private List<String> estadoCivil = null;
	// Acesso � lista de idiomas.
	private List<String> idiomas = null;
	
	// Descri��o da habilidade do aluno.
	private String descricaoHabilidade = "";
	// N�vel de habilidade do aluno.
	private String nivelHabilidade = "";
	// Lista de habilidades do aluno.
	private List<Habilidade> habilidades = null;
	// Habilidade selecionada para ser excluida da tabela.
	private Habilidade habilidadeSelecionada;
	
	// Descri��o da l�ngua falada pelo aluno.
	private String descricaoLingua = "";
	// N�vel de flu�ncia na l�ngua falada pelo aluno.
	private String nivelLingua = "";
	// Lista de l�nguas faladas pelo aluno.
	private List<Habilidade> linguas = null;
	// L�ngua selecionada para ser excluida da tabela.
	private Habilidade linguaSelecionada;
	
	
	
	
	/**
	 * Construtor da classe.
	 * Carrega os dados do aluno para exibir na p�gina de consulta de perfil.
	 * Inicia as listas utilizadas por essa classe.
	 */
	public CadastrarAlunoController() {		
		carregaDadosDoAluno();		
		iniciaListas();
	}

	
	
	/**
	 * Inicia as listas utilizadas por esta classe.
	 */
	private void iniciaListas() {
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
		this.habilidades = new ArrayList<Habilidade>();
		this.linguas = new ArrayList<Habilidade>();		
	}




	/**
	 * Carrega os dados do aluno logado no sistema.
	 * 
	 * Utiliza o email e senha do usu�rio logado para buscar
	 * no banco de dados o seu registro completo. 
	 * Os dados buscados ser�o distribu�dos nos campos do perfil do aluno.
	 */
	private void carregaDadosDoAluno() {
		// Recupera os dados da pessoa na sess�o atual.
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoa != null) {
			
			String email = pessoa.getContato().getEmail();
			String senha = pessoa.getSenha();
			
			this.aluno = new AlunoDAO().buscarAluno(email, senha);
		} else {
			this.aluno = new Aluno();
		}		
	}

	
	
	/**
	 * Executa este m�todo para atualiza��o do registro 
	 * quando o usu�rio editar o perfil.
	 */
	public void atualizarAluno() {
		System.out.println("Atualizando aluno: " + aluno);
		
		boolean atualizou = new AlunoDAO().atualizar(aluno);		
		
		if(atualizou){
			Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
			
			atualizaUsuarioDaSession();
		}
	}
	
	
	/**
	 * Atualiza os dados do usu�rio na Session atual.
	 */
	private void atualizaUsuarioDaSession() {
		// Atualiza os dados do objeto Session.
		// Necess�rio para atualizar as informa��es do aluno no painel do perfil.
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		pessoa.setNome(aluno.getNome());
		pessoa.getEndereco().setCidade(aluno.getEndereco().getCidade());
		pessoa.getContato().setEmail(aluno.getContato().getEmail());
		pessoa.setCurso(aluno.getCurso());

		// Desfaz a sess�o atual e constroi outra com os dados atualizados.
		SessionUtil.invalidate();
		SessionUtil.setParam(SessionUtil.KEY_SESSION, pessoa);		
	}




	/**
	 * Retorna uma lista de todos os alunos cadastrados no banco de dados.
	 */
	public ArrayList<Aluno> buscarAluno(){
		return (ArrayList<Aluno>) (new AlunoDAO().listar());
	}
	
	
	/**
	 * Adiciona na lista uma nova habilidade cadastrada.
	 */
	public void addHabilidade(){
		
		descricaoHabilidade = descricaoHabilidade.toUpperCase();
		
		if(!descricaoHabilidade.isEmpty())
			if(!verificaExistenciaDeHabilidade(descricaoHabilidade))
				this.habilidades.add(new Habilidade(descricaoHabilidade, nivelHabilidade));
				
				String nome = descricaoHabilidade;
				String nivel = nivelHabilidade;
		
				Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
				String email = pessoa.getContato().getEmail();
				String senha = pessoa.getSenha();
					
				AlunoDAO aluno = new AlunoDAO();
				aluno.addHabilidade(email, senha, nome, nivel );	
	}

	
	/**
	 * Verifica se na lista de habilidades j� n�o existe a habilidade
	 * que se deseja cadastrar.
	 * 
	 * @param descricao A descri��o da nova habilidade
	 * @return verdadeiro ou falso sobre a exist�ncia da nova habilidade.
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
	 * Exclui uma habilidade da lista de habilidades do aluno.
	 */
	public void excluiHabilidade(){
		if(habilidadeSelecionada != null)
			this.habilidades.remove(habilidadeSelecionada);
	}
	
	/**
	 * Adiciona uma l�ngua na lista de l�nguas faladas pelo aluno.
	 */
	public void addLingua(){
		if(!verificaExistenciaDeLingua(descricaoLingua))
			this.linguas.add(new Habilidade(descricaoLingua, nivelLingua));
	}
	
	/**
	 * Exclui uma l�ngua da lista de l�nguas faladas pelo aluno.
	 */
	public void excluiLingua(){
		if(linguaSelecionada != null)
			this.linguas.remove(linguaSelecionada);
	}
	
	
	/**
	 * Verifica se na lista de l�nguas j� n�o existe a l�ngua
	 * que se deseja cadastrar.
	 * 
	 * @param descricaoLingua o nome ou descri��o da nova l�ngua.
	 * @return verdadeiro ou falso sobre a exist�ncia da nova l�ngua.
	 */
	private boolean verificaExistenciaDeLingua(String descricaoLingua){		
		for (Habilidade lingua : linguas) {
			if(lingua.getDescricao().equals(descricaoLingua)){
				return true;
			}
		}		
		return false;
	}
	
	
	
	
	/**Inst�ncia do aluno logado no sistema.*/
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public List<String> getEstadosBrasileiros() {
		return estadosBrasileiros;
	}
	public void setEstadosBrasileiros(List<String> estadosBrasileiros) {
		this.estadosBrasileiros = estadosBrasileiros;
	}
	public List<String> getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(List<String> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public List<String> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(List<String> idiomas) {
		this.idiomas = idiomas;
	}
	public List<Habilidade> getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
	public List<Habilidade> getLinguas() {
		return linguas;
	}
	public void setLinguas(List<Habilidade> linguas) {
		this.linguas = linguas;
	}
	public String getDescricaoHabilidade() {
		return descricaoHabilidade;
	}
	public void setDescricaoHabilidade(String descricaoHabilidade) {
		this.descricaoHabilidade = descricaoHabilidade;
	}
	public String getNivelHabilidade() {
		return nivelHabilidade;
	}
	public void setNivelHabilidade(String nivelHabilidade) {
		this.nivelHabilidade = nivelHabilidade;
	}
	public String getDescricaoLingua() {
		return descricaoLingua;
	}
	public void setDescricaoLingua(String descricaoLingua) {
		this.descricaoLingua = descricaoLingua;
	}
	public String getNivelLingua() {
		return nivelLingua;
	}
	public void setNivelLingua(String nivelLingua) {
		this.nivelLingua = nivelLingua;
	}
	public Habilidade getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}
	public void setHabilidadeSelecionada(Habilidade habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}
	public Habilidade getLinguaSelecionada() {
		return linguaSelecionada;
	}
	public void setLinguaSelecionada(Habilidade linguaSelecionada) {
		this.linguaSelecionada = linguaSelecionada;
	}
	
	
}
