package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.pojo.Aluno;

@ManagedBean
@ViewScoped
public class BuscarAlunosController implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private List<Aluno> alunos;
	
	
	public BuscarAlunosController() { 
		this.alunos = new AlunoDAO().listar();
	}


	
	
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	
	
}
