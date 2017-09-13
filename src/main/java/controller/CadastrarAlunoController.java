package controller;

import java.util.ArrayList;
import java.util.Date;
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

	// Instância do aluno logado no sistema.
	private Aluno aluno = null;
	// Acesso à lista de estados brasileiros.
	private List<String> estadosBrasileiros = null;
	// Acesso à lista de estado cívil.
	private List<String> estadoCivil = null;
	// Acesso à lista de idiomas.
	private List<String> idiomas = null;
	// Necessário para bloqueio de datas no calendário.
	private Date dataMaxima = new Date();
	
	
	
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
		System.out.println("ATUALIZANDO ALUNO: " + aluno);
		boolean atualizou = new AlunoDAO().atualizar(aluno);		
		
		if(atualizou){
			Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
		}
	}
	
	
	/**
	 * Retorna uma lista de todos os alunos cadastrados no banco de dados.
	 */
	public ArrayList<Aluno> buscarAluno(){
		return (ArrayList<Aluno>) (new AlunoDAO().listar());
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
	public Date getDataMaxima() {
		return dataMaxima;
	}
	public void setDataMaxima(Date dataMaxima) {
		this.dataMaxima = dataMaxima;
	}
	
}
