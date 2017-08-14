package controller;

import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

import model.Login;

@ManagedBean
public class LoginController {

	
	private Login login;
	
	
	public LoginController() {
		this.login = new Login();
	}
	
	
	
	public Login getLogin(){
		return login;
	}
	
	
	
	public void clickNovoUsuario() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('newUserDialog').show()");
        
        System.out.println("Usuário ("+login.getUsuario()+" - "+login.getSenha()+") acessando o sistema...");
    }
	

	
	public void clickNovoAluno() {
		System.out.println("Cadastrar um novo aluno...");
	}
	
	public void clickNovoProfessor() {
		System.out.println("Cadastrar um novo professor...");
	}
	
	
	
	
	
	
}
