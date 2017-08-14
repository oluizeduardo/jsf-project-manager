package model;

public class Login {

	private String usuario;
	private String senha;
	private boolean lembrarSenha = false;
	

	public Login() {	}
	
	
	
	public Login(String user, String password){
		this.usuario = user;
		this.senha = password;
	}
	
	
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



	public boolean isLembrarSenha() {
		return lembrarSenha;
	}



	public void setLembrarSenha(boolean lembrarSenha) {
		this.lembrarSenha = lembrarSenha;
	}

}
