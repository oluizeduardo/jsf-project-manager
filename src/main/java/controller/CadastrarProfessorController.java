package controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Pessoa;
import model.pojo.Professor;
import view.Mensagem;
import web.SessionUtil;
import model.dao.AlunoDAO;
import model.dao.ProfessorDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;
import model.helperView.ListaDeIdiomas;


@ManagedBean(name = "cadastrarProfessorController")
@ViewScoped
public class CadastrarProfessorController {

	private Professor professor = null;
	private List<String> estadosBrasileiros = null;
	private List<String> estadoCivil = null;
	private List<String> idiomas = null;
	
	private String senhaAntiga = null;
	private String novaSenha = null;
	
	
	public CadastrarProfessorController() {
		
		// Carrega os dados do professor logado no sistema.
		carregaDadosDoProfessor();
			
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
	}
	

	
	/**
	 * Carrega os dados do professor logado no sistema.
	 */
	private void carregaDadosDoProfessor() {
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoa != null) {
			this.professor = new ProfessorDAO().buscarProfessor(pessoa.getContato().getEmail(), pessoa.getSenha());
		} else {
			this.professor = new Professor();
		}
	}


	public void salvarProfessor(){
		
		boolean cadastrou = new ProfessorDAO().salvar(professor);
		
		if(cadastrou){
			Mensagem.ExibeMensagem("Dados atualizados com sucesso!");
		}else{
			Mensagem.ExibeMensagemErro("Não foi possível atualizar os dados.");
		}
	}
	
	
	public void alterarSenha(){
		System.out.println("Alterando a senha do professor...");
	}
	
	public void atualizarProfessor() {
		System.out.println("ATUALIZANDO PROF: " + professor);
		new ProfessorDAO().atualizar(professor);		
		Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
	}
	
	
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setAluno(Professor prof) {
		this.professor = prof;
	}
	
	public List<String> getEstadosBrasileiros() {
		return estadosBrasileiros;
	}
	
	public List<String> getEstadoCivil() {
		return estadoCivil;
	}
	
	public List<String> getIdiomas() {
		return idiomas;
	}
	
	
}
