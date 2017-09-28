package model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.dao.ProjetoDAO;
import model.pojo.Pessoa;
import model.pojo.Projeto;
import web.SessionUtil;


@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Projeto> todosProjetos = null;
	private List<Projeto> projetosQueParticipo = null;
	
	
	public ProjetoBean() {
		
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = pessoa.getContato().getEmail();
		String senha = pessoa.getSenha();
		
		this.todosProjetos = new ProjetoDAO().listar();
		this.projetosQueParticipo = new AlunoDAO().getProjetosQueParticipa(email, senha);
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
	
}
