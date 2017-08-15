package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Usuario;


@ManagedBean
@ViewScoped
public class LoginController {

	
	private Usuario usuario;
	
	
	public LoginController() {
		this.usuario = new Usuario();
	}
	
	
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	
	
	
	
}
