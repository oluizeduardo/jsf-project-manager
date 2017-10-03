package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Aluno;
import model.pojo.Habilidade;
import model.pojo.Idioma;
import model.pojo.Pessoa;
import view.Mensagem;
import web.SessionUtil;
import model.dao.AlunoDAO;
import model.dao.HabilidadeDAO;
import model.dao.IdiomaDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;
import model.helperView.ListaDeIdiomas;



@ManagedBean(name = "cadastrarAlunoController")
@ViewScoped
public class CadastrarAlunoController {

	// Instância do aluno logado no sistema.
	private Aluno aluno = null;
	// Acesso à lista de estados brasileiros.
	private List<String> estadosBrasileiros = null;
	// Acesso à lista de estado cívil.
	private List<String> estadoCivil = null;
	// Acesso à lista de idiomas.
	private List<String> idiomas = null;
	
	// Descrição da habilidade do aluno.
	private String descricaoHabilidade = "";
	// Nível de habilidade do aluno.
	private String nivelHabilidade = "";
	// Lista de habilidades do aluno.
	private List<Habilidade> habilidades = null;
	// Habilidade selecionada para ser excluida da tabela.
	private Habilidade habilidadeSelecionada;
	
	// Descrição da língua falada pelo aluno.
	private String nomeIdioma = "";
	// Nível de fluência na língua falada pelo aluno.
	private String nivelDeConhecimento = "";
	// Lista de línguas faladas pelo aluno.
	private List<Idioma> linguasFaladasPeloAluno = null;
	// Língua selecionada para ser excluida da tabela.
	private Idioma linguaSelecionada;
	
	
	
	
	/**
	 * Construtor da classe.
	 * Carrega os dados do aluno para exibir na página de consulta de perfil.
	 * Inicia as listas utilizadas por essa classe.
	 */
	public CadastrarAlunoController() {		
		iniciaListas();
		carregaDadosDoAluno();		
	}

	
	
	/**
	 * Inicia as listas utilizadas por esta classe.
	 */
	private void iniciaListas() {
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
		this.habilidades = new ArrayList<Habilidade>();
		this.linguasFaladasPeloAluno = new ArrayList<Idioma>();		
	}




	/**
	 * Carrega os dados do aluno logado no sistema.
	 * 
	 * Utiliza o email e senha do usuário logado para buscar
	 * no banco de dados o seu registro completo. 
	 * Os dados buscados serão distribuídos nos campos do perfil do aluno.
	 */
	private void carregaDadosDoAluno() {
		// Recupera os dados da pessoa na sessão atual.
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoa != null) {
			
			String email = pessoa.getContato().getEmail();
			String senha = pessoa.getSenha();
			
			this.aluno = new AlunoDAO().buscarAluno(email, senha);
			setHabilidades(aluno.getHabilidades());
			setLinguasFaladasPeloAluno(aluno.getIdiomas());			
			
		} else {
			this.aluno = new Aluno();
		}		
	}

	
	
	/**
	 * Executa este método para atualização do registro 
	 * quando o usuário editar o perfil.
	 */
	public void atualizarAluno() {
		System.out.println("Atualizando aluno: " + aluno);
		
		this.aluno.setHabilidades(habilidades);
		this.aluno.setIdiomas(linguasFaladasPeloAluno);
		
		if(validaData(aluno.getDataNascimento())){
			boolean atualizou = new AlunoDAO().atualizar(aluno);
			
			if(atualizou){
				atualizaUsuarioDaSession();
				Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
			}else{
				Mensagem.ExibeMensagemErro("Erro ao atualizar registro!");
			}
		}else{
			Mensagem.ExibeMensagemAtencao("Informe corretamente a data de nascimento.");
		}
	}
	
	
	/**
	 * Valida a data de aniversário do aluno.
	 */
	private boolean validaData(String datastr){
		String str_dataInicio = new String(datastr);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
	     	format.setLenient(false);
	     	format.parse(str_dataInicio);
	     	
	     	return true;
		} catch (ParseException e) {
		    return false;
		}
	}
	
	
	
	/**
	 * Atualiza os dados do usuário na Session atual.
	 */
	private void atualizaUsuarioDaSession() {
		// Atualiza os dados do objeto Session.
		// Necessário para atualizar as informações do aluno no painel do perfil.
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		pessoa.setNome(aluno.getNome());
		pessoa.getEndereco().setCidade(aluno.getEndereco().getCidade());
		pessoa.getContato().setEmail(aluno.getContato().getEmail());
		pessoa.setCurso(aluno.getCurso());

		// Desfaz a sessão atual e constroi outra com os dados atualizados.
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
		 		
 		if(!descricaoHabilidade.isEmpty()){
 			if(!verificaExistenciaDeHabilidade(descricaoHabilidade)){
 				
 				Habilidade novaHabilidade = new Habilidade(descricaoHabilidade, nivelHabilidade);
 				this.habilidades.add(novaHabilidade);
 				aluno.setHabilidades(habilidades);
 				new HabilidadeDAO().atualizar(aluno, novaHabilidade);
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
		if(habilidadeSelecionada != null){
			this.habilidades.remove(habilidadeSelecionada);
			new HabilidadeDAO().excluiHabilidade(aluno, habilidadeSelecionada);
		}
	}
	
	
	
	/**
	 * Adiciona uma língua na lista de línguas faladas pelo aluno.
	 */
	public void addLingua(){		
		if(!verificaExistenciaDeLingua(nomeIdioma)){
			Idioma novoIdioma = new Idioma(nomeIdioma, nivelDeConhecimento);
			this.linguasFaladasPeloAluno.add(novoIdioma);
			aluno.setIdiomas(linguasFaladasPeloAluno);
			new IdiomaDAO().atualizar(aluno, novoIdioma);
		}
	}
	
	
	/**
 	 * Verifica se na lista de línguas já não existe a língua
 	 * que se deseja cadastrar.
 	 * 
 	 * @param nomeLingua o nome da nova língua.
 	 * @return verdadeiro ou falso sobre a existência da nova língua.
 	 */
 	private boolean verificaExistenciaDeLingua(String nomeLingua){		      
 		for (Idioma idioma : linguasFaladasPeloAluno) {
 			if(idioma.getNomeIdioma().equals(nomeLingua)){
 				return true;
 			}
 		}		
 		return false;
 	}
	
	
	/**
	 * Exclui uma língua da lista de línguas faladas pelo aluno.
	 */
	public void excluiLingua(){
		if(linguaSelecionada != null){
			this.linguasFaladasPeloAluno.remove(linguaSelecionada);
			new IdiomaDAO().excluiIdioma(aluno, linguaSelecionada);
		}
	}
	
	
	/**Instância do aluno logado no sistema.*/
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
	public List<Idioma> getLinguasFaladasPeloAluno() {
		return linguasFaladasPeloAluno;
	}
	public void setLinguasFaladasPeloAluno(List<Idioma> linguas) {
		this.linguasFaladasPeloAluno = linguas;
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
	public String getNomeIdioma() {
		return nomeIdioma;
	}
	public void setNomeIdioma(String nomeIdioma) {
		this.nomeIdioma = nomeIdioma;
	}	
	public String getNivelDeConhecimento() {
		return nivelDeConhecimento;
	}
	public void setNivelDeConhecimento(String nivelDeConhecimento) {
		this.nivelDeConhecimento = nivelDeConhecimento;
	}
	public Habilidade getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}
	public void setHabilidadeSelecionada(Habilidade habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}
	public Idioma getLinguaSelecionada() {
		return linguaSelecionada;
	}
	public void setLinguaSelecionada(Idioma linguaSelecionada) {
		this.linguaSelecionada = linguaSelecionada;
	}
	
	
}
