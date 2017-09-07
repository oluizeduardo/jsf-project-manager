package controller;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.dao.LoginDAO;
import model.pojo.Pessoa;
import view.Mensagem;
import web.SessionUtil;


@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

	private Pessoa usuario;
	private LoginDAO loginDAO;

	
	
	public LoginController() {
		this.usuario = new Pessoa();
		this.loginDAO = new LoginDAO();
	}
	
	
	
	
	public void acessar() {

		String userName = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();

		boolean existeUsuario = loginDAO.validaLogin(userName, password);
		
		if (existeUsuario) {
			
			SessionUtil.setParam(SessionUtil.KEY_SESSION, usuario);
			System.out.println("Logado com sucesso!");
			System.out.println(SessionUtil.getParam("user-logged"));
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {
			Mensagem.ExibeMensagemAtencao("Nenhum usuário foi encontrado com esses dados.");
		}
	}

	
	
	public Pessoa getUsuario() {
		return usuario;
	}
}
