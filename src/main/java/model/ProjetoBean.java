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
	
	
	public ProjetoBean() { }

	
	public List<Projeto> getTodosProjetos() {
		this.todosProjetos = new ProjetoDAO().listar();
		return todosProjetos;
	}	
	
	
	public List<Projeto> getProjetosQueParticipo() {
		this.projetosQueParticipo = new AlunoDAO().getProjetosQueParticipa();
		return projetosQueParticipo;
	}
	
}
