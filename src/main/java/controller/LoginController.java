package controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;

import model.dao.LoginDAO;
import model.pojo.Usuario;


@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController {

	/**
	 * Objeto do usuário que acessa o sistema.
	 */
	private Usuario usuario;
	private LoginDAO loginDAO = new LoginDAO();
	
	public LoginController() {
		this.usuario = new Usuario();
	}
	
	public void acessar(){
		
		String user = this.usuario.getContato().getEmail();
		String password = this.usuario.getSenha();
		
		 Usuario existeUsuario = loginDAO.verificarLogin(user, password);
		
		
		//System.out.println("Acessando sistema... USUÁRIO: '"+user+"' SENHA: '"+password+"'");
		
//		if(existeUsuario) {
//			System.out.println("AMEM DEUS");
//			
////			try {
////				FacesContext.getCurrentInstance().getExternalContext().redirect("aluno/home.xhtml");
////			} catch (IOException e) { 
////				System.err.println(e.getMessage());
////			}
//		}
//		else {
//			System.out.println("FUDEU");
//		}
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
}
