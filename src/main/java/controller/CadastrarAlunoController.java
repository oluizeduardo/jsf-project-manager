package controller;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.pojo.Aluno;
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
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	
	
	public CadastrarAlunoController() {
		this.aluno = new Aluno();
		aluno = alunoDAO.buscarAlunoEmail("fabiano@univas.edu.br");
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
	}


	
	public void salvarAluno(){
		System.out.println("Salvando aluno: " + aluno.getNome());
		alunoDAO = new AlunoDAO();
		alunoDAO.salvarAluno(aluno);
		
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
		} 
		catch (IOException e) { 
			System.err.println(e.getMessage());
		}
	}
	
	public void atualizarAluno() {
		System.out.println("Atualizando aluno:" + aluno);
		alunoDAO = new AlunoDAO();
		alunoDAO.atualizarAluno(aluno);
		
	}
	
	public void salvarHabilidade() {
		
	}
	
	public ArrayList<Aluno> buscarAluno(){
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		return alunos;
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
}
