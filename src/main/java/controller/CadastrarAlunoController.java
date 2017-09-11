package controller;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

	private Aluno aluno = null;
	private List<String> estadosBrasileiros = null;
	private List<String> estadoCivil = null;
	private List<String> idiomas = null;
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
	 */
	private void carregaDadosDoAluno() {
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoa != null) {
			
			String email = pessoa.getContato().getEmail();
			String senha = pessoa.getSenha();
			
			this.aluno = new AlunoDAO().buscarAluno(email, senha);
		} else {
			this.aluno = new Aluno();
		}		
	}

	
	
	public void salvarAluno(){		
		new AlunoDAO().salvar(aluno);
		
		try {			
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
		} 
		catch (IOException e) { System.err.println(e.getMessage());	}
	}
	
	
	/**
	 * Executa este método para atualização do registro quando o usuário editar o perfil.
	 */
	public void atualizarAluno() {
		System.out.println("ATUALIZANDO ALUNO: " + aluno);
		new AlunoDAO().atualizar(aluno);		
		Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e1) { e1.printStackTrace(); }
//		
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//		}
	}
	
	
	public ArrayList<Aluno> buscarAluno(){
		return (ArrayList<Aluno>) (new AlunoDAO().listar());
	}
	
	
	
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
