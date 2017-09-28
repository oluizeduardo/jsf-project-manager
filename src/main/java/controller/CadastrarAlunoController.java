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

	// Instância do aluno logado no sistema.
	private Aluno aluno = null;
	// Acesso à lista de estados brasileiros.
	private List<String> estadosBrasileiros = null;
	// Acesso à lista de estado cívil.
	private List<String> estadoCivil = null;
	// Acesso à lista de idiomas.
	private List<String> idiomas = null;
	// Habilidade do aluno.
	private Habilidade habilidade = new Habilidade();
	// Lpingua falada pelo aluno.
	private Habilidade lingua = new Habilidade();
	// Lista de habilidades do aluno.
	private List<Habilidade> habilidades = null;
	// Lista de línguas faladas pelo aluno.
	private List<Habilidade> linguas = null;
	
	
	
	public CadastrarAlunoController() {		
		// Carrega os dados do aluno logado no sistema.
		carregaDadosDoAluno();
		
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
		this.habilidades = new ArrayList<Habilidade>();
		this.linguas = new ArrayList<Habilidade>();
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
		
		boolean atualizou = new AlunoDAO().atualizar(aluno);		
		
		if(atualizou){
			Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
			
			atualizaUsuarioDaSession();
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
	
	
	public void addHabilidade(){
		this.habilidades.add(this.habilidade);
	}
	
	public void excluirHabilidade(){
		//this.habilidades.remove(habilidade);
		System.out.println("REMOVENDO HABILIDADE...");
	}
	
	public void addLingua(){
		this.linguas.add(this.lingua);
	}
	
	public void excluirLingua(){
		//this.habilidades.remove(habilidade);
		System.out.println("REMOVENDO HABILIDADE...");
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
	public List<Habilidade> getLinguas() {
		return linguas;
	}
	public void setLinguas(List<Habilidade> linguas) {
		this.linguas = linguas;
	}
	public Habilidade getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}
	public Habilidade getLingua() {
		return lingua;
	}
	public void setLingua(Habilidade lingua) {
		this.lingua = lingua;
	}
	
}
