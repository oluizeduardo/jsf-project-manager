package model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.dao.ProjetoDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import web.SessionUtil;


@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Projeto> todosProjetos = null;
	private List<Projeto> projetosQueParticipo = null;
	private List<Projeto> projetosRecomendados = null;
	
	
	public ProjetoBean() {
		
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = pessoa.getContato().getEmail();
		String senha = pessoa.getSenha();
		
		AlunoDAO alunoDAO = new AlunoDAO();
		
		Aluno aluno = alunoDAO.buscarAluno(email, senha);
		this.todosProjetos = new ProjetoDAO().listar();
		this.projetosQueParticipo = alunoDAO.getProjetosQueParticipa(aluno);
		this.projetosRecomendados = alunoDAO.getProjetosRecomendados(aluno, todosProjetos);
	}

	
	public List<Projeto> getTodosProjetos() {		
		return todosProjetos;
	}	
	public void setTodosProjetos(List<Projeto> todosProjetos) {		
		this.todosProjetos = todosProjetos;
	}
	public List<Projeto> getProjetosQueParticipo() {
		return projetosQueParticipo;
	}
	public List<Projeto> getProjetosRecomendados() {
		return projetosRecomendados;
	}
	public void setProjetosRecomendados(List<Projeto> projetosRecomendados) {
		this.projetosRecomendados = projetosRecomendados;
	}
	
}
