package controller;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.dao.LoginDAO;
import model.pojo.Usuario;

@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

	private Usuario usuario;
	private LoginDAO loginDAO = new LoginDAO();

	public LoginController() {
		this.usuario = new Usuario();
	}

	public void acessar() {

		String user = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();

		boolean existeUsuario = loginDAO.verificarLogin(user, password);

		if (existeUsuario) {
			System.out.println("Logado com sucesso!");

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} else {
			System.out.println("Não logou!");
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
