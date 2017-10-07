package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import web.SessionUtil;

@ManagedBean
@ViewScoped
public class BuscarAlunosController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Todos alunos cadastrados.
	private List<Aluno> alunos;
	// Lista de alunos indicados para um professor.
	private List<Aluno> alunosIndicados = new ArrayList<Aluno>();
	
	
	public BuscarAlunosController() { 
		
		// Retorna os dados do professor logado.
		Pessoa donoDoProjeto = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		AlunoDAO alunoDAO = new AlunoDAO();
		this.alunos = alunoDAO.listar();
		this.alunosIndicados = alunoDAO.getListaDeAlunosIndicados(donoDoProjeto);
	}


	
	
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	public List<Aluno> getAlunosIndicados() {
		return alunosIndicados;
	}
	public void setAlunosIndicados(List<Aluno> alunosIndicados) {
		this.alunosIndicados = alunosIndicados;
	}
	
	
	
}
