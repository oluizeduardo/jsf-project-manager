package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.dao.AlunoDAO;
import model.dao.ProfessorDAO;
import model.pojo.Aluno;
import model.pojo.Pessoa;
import model.pojo.Professor;
import view.Mensagem;
import web.SessionUtil;

@ManagedBean(name = "novoUsuarioController")
@ViewScoped
public class NovoUsuarioController {

	// Contantes para definição do perfil de usuário.
	private final String PERFIL_ALUNO = "Aluno";
	private final String PERFIL_PROFESSOR = "Professor";
	
	
	// Objeto pessoa salva os dados iniciais do novo usuário.
	private Pessoa novoUsuario = null;
	
	
	
	public NovoUsuarioController() {
		this.novoUsuario = new Pessoa();
	}

	
	
	
	/**
	 * Método executado no início, quando um novo usuário 
	 * se cadastra no sistema. 
	 * Após a realização do cadastro, verifica se o novo usuário 
	 * é um Aluno ou Professor e redireciona-o para a página inicial da 
	 * aplicação.
	 */
	public void salvarNovoUsuario(){
		String nome  = novoUsuario.getNome();
		String email = novoUsuario.getContato().getEmail();
		String senha = novoUsuario.getSenha();
		String papel = novoUsuario.getPapel();//Papel: Aluno ou Professor.
		String curso = novoUsuario.getCurso().getNome();
		
		String tipoDeUsuario = "";// "aluno" ou "professor".
		boolean cadastrou = false;// status do cadastro.		
				
		// Verifica que tipo de usuário quer fazer o cadastro.
		if(papel.equals(PERFIL_ALUNO)){
			cadastrou = new AlunoDAO().salvar(new Aluno(nome, curso, email, senha));
			tipoDeUsuario = "aluno";
			
		}else if(papel.equals(PERFIL_PROFESSOR)){
			cadastrou = new ProfessorDAO().salvar(new Professor(nome, curso, email, senha));
			tipoDeUsuario = "professor";
		}
		
		if(cadastrou){
			// Inicia uma sessão para um Aluno.
			SessionUtil.setParam(SessionUtil.KEY_SESSION, novoUsuario);		
			
			try {	
				// Redireciona para o home do novo usuário.
				FacesContext.getCurrentInstance().getExternalContext().redirect(tipoDeUsuario+"/home.xhtml");
			} 
			catch (IOException e) { 
				System.out.println("Erro ao redirecionar para a home do novo usuário - "
						+ "NovoUsuarioController.salvarNovoUsuario()");
				Mensagem.ExibeMensagemErro("Erro ao redirecionar novo usuário.");
			}
		}else{
			System.out.println("Erro no novo cadastro - NovoUsuarioController.salvarNovoUsuario()");
			Mensagem.ExibeMensagemErro("Erro no novo cadastro.");
		}
	}
	
	
	
	
	public Pessoa getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Pessoa novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	
}
