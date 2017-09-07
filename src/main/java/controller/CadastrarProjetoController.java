package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.neo4j.driver.v1.Value;
import model.dao.ProjetoDAO;
import model.pojo.Projeto;

@ManagedBean(name = "cadastrarProjetoController")
@ViewScoped
public class CadastrarProjetoController {

	private Projeto projeto = null;

	private String cursoEnvolvido = null;

	public CadastrarProjetoController() {
		this.projeto = new Projeto();
	}

	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/yyyy");
		return sdf.format(new Date());
	}

	public void salvarProjeto() {
		projeto.setDataPublicacao(getCurrentDate());
		new ProjetoDAO().salvar(projeto);
	}

	public ArrayList<Projeto> buscarProjeto() {
		return (ArrayList<Projeto>) (new ProjetoDAO().listar());
	}

	public String getCursoEnvolvido() {
		return cursoEnvolvido;
	}

	public void setCursoEnvolvido(String cursoEnvolvido) {
		this.cursoEnvolvido = cursoEnvolvido;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

}
