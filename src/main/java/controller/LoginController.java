package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.dao.LoginDAO;
import model.pojo.Pessoa;
import view.Mensagem;
import web.SessionUtil;

@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	// Salva os dados b�sicos de uma pessoa que est� acessando o sistema.
	private Pessoa usuario;

	
	
	public LoginController() {
		this.usuario = new Pessoa();
	}

	
	
	public void validarAcessar() {

		// Dados de usu�rio e senha informados na tela de login.
		String userName = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();

		// Valida��o dos campos da tela de login.
		if (userName.isEmpty() && password.isEmpty()) {
			Mensagem.ExibeMensagem("Informe um email e senha v�lidos.");
		} else {
			if (userName.isEmpty()) {
				Mensagem.ExibeMensagem("Preencha o campo com um email v�lido.");
			} else {
				if (password.isEmpty()) {
					Mensagem.ExibeMensagem("Informe a senha do usu�rio.");
				} else {

					
					// Busca no banco um registro baseado no email e senha informado.
					Pessoa objPessoa = new LoginDAO().buscaPessoa(userName, password);					
					
					// Se o objeto n�o for nulo � porque existe o usu�rio cadastrado.
					if (objPessoa != null) {

						if (objPessoa.getPapel().equals("Aluno")) {

							
							// Inicia uma sess�o para um Aluno.
							SessionUtil.setParam(SessionUtil.KEY_SESSION, objPessoa);

							
							System.out.println("Aluno acessando o sistema...");
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
							} catch (IOException e) {
								System.err.println(e.getMessage());
							}
						} else {

							
							// Inicia uma sess�o para um Professor.
							SessionUtil.setParam(SessionUtil.KEY_SESSION, objPessoa);

							
							System.out.println("Professor acessando o sistema...");
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("professor/home.xhtml");
							} catch (IOException e) {
								System.err.println(e.getMessage());
							}
						}

					} else {
						Mensagem.ExibeMensagemAtencao("Nenhum usu�rio foi encontrado com esses dados.");
					}
				}
			}
		}
	}

	public Pessoa getUsuario() {
		return usuario;
	}
}
