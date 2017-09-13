package model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.AlunoDAO;
import model.dao.ProjetoDAO;
import model.pojo.Projeto;


@ManagedBean
@ViewScoped
public class ProjetoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private List<Projeto> todosProjetos = null;
	private List<Projeto> projetosQueParticipo = null;
	
	
	public ProjetoBean() {
		this.todosProjetos = new ProjetoDAO().listar();
		this.projetosQueParticipo = new AlunoDAO().getProjetosQueParticipa();
	}

	
	public List<Projeto> getTodosProjetos() {		
		return todosProjetos;
	}	
	
	
	public List<Projeto> getProjetosQueParticipo() {
		return projetosQueParticipo;
	}
	
}
