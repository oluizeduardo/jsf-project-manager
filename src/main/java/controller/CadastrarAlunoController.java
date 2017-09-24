package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Aluno;
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
	
	
	
	public CadastrarAlunoController() {		
		// Carrega os dados do aluno logado no sistema.
		carregaDadosDoAluno();
		
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
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
	}
	
	
	/**
	 * Retorna uma lista de todos os alunos cadastrados no banco de dados.
	 */
	public ArrayList<Aluno> buscarAluno(){
		return (ArrayList<Aluno>) (new AlunoDAO().listar());
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
	
}
