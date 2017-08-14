package model;

public class Acesso {

	private String nome_de_usuario;
	private String senha;
	
	
	
	public Acesso(String usuario, String senha) {
		this.nome_de_usuario = usuario;
		this.senha = senha;
	}

	
	public String getUsuario() {
		return nome_de_usuario;
	}

	public void setUsuario(String usuario) {
		this.nome_de_usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
