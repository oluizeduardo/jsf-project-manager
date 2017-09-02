package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.pojo.Usuario;


@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

	/**
	 * Objeto do usu�rio que acessa o sistema.
	 */
	private Usuario usuario;
	
	
	
	public LoginController() {
		this.usuario = new Usuario();
	}
	
	
	
	
	public void acessar(){
		
		String user = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();
		
		System.out.println("Acessando sistema... USU�RIO: '"+user+"' SENHA: '"+password+"'");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
		} catch (IOException e) { 
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	
	
	
	
}
