package controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Pessoa;
import model.pojo.Professor;
import view.Mensagem;
import web.SessionUtil;
import model.dao.ProfessorDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;


@ManagedBean(name = "cadastrarProfessorController")
@ViewScoped
public class CadastrarProfessorController {

	// Instância do professor logado no sistema.
	private Professor professor = null;
	// Acesso à lista de estados brasileiros.
	private List<String> estadosBrasileiros = null;
	// Acesso á lista de estado cívil.
	private List<String> estadoCivil = null;
	
	
	public CadastrarProfessorController() {
		// Carrega os dados do professor logado no sistema.
		carregaDadosDoProfessor();
			
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
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


	
	
	/**
	 * Atualiza no banco de dados o registro do professor logado no sistema.
	 */
	public void atualizarProfessor() {
		System.out.println("Atualizando professor: " + professor);
		
		boolean atualizou = new ProfessorDAO().atualizar(professor);
		
		if(atualizou){
			Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
			
			// Atualiza os dados do objeto Session.
			// Necessário para atualizar as informações do professor no painel do perfil.
			Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
			pessoa.setNome(professor.getNome());
			pessoa.getEndereco().setCidade(professor.getEndereco().getCidade());
			pessoa.getContato().setEmail(professor.getContato().getEmail());
			pessoa.setCurso(professor.getCurso());

			// Desfaz a sessão atual e constroi outra com os dados atualizados.
			SessionUtil.invalidate();
			SessionUtil.setParam(SessionUtil.KEY_SESSION, pessoa);
		}
	}
	
	

	
	/**Retona a instância do atual professor logado no sistema.*/
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
	
}
