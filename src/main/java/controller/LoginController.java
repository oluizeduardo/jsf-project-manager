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

	public void validarAcessar() {

		String userName = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();

		if (userName.isEmpty() && password.isEmpty()) {
			Mensagem.ExibeMensagem("Informe um email e senha válidos.");
		} else {
			if (userName.isEmpty()) {
				Mensagem.ExibeMensagem("Preencha o campo com um email válido.");
			} else {
				if (password.isEmpty()) {
					Mensagem.ExibeMensagem("Informe a senha do usuário.");
				} else {

					Pessoa objPessoa = loginDAO.buscaPessoa(userName, password);

					if (objPessoa != null) {

						if (objPessoa.getPapel().equals("Aluno")) {

							SessionUtil.setParam(SessionUtil.KEY_SESSION, usuario);

							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
							} catch (IOException e) {
								System.err.println(e.getMessage());
							}
						} else if (objPessoa.getPapel().equals("Professor")) {

							SessionUtil.setParam(SessionUtil.KEY_SESSION, usuario);

							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("professor/home.xhtml");
							} catch (IOException e) {
								System.err.println(e.getMessage());
							}
						}

					} else {
						Mensagem.ExibeMensagemAtencao("Nenhum usuário foi encontrado com esses dados.");
					}
				}
			}
		}
	}

	public Pessoa getUsuario() {
		return usuario;
	}
}
