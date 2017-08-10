package model;

public class Acesso {

	private String nome_de_usuario;
	private String senha;
	
	public Acesso(String nome, String senha) {
		this.nome_de_usuario = nome;
		this.senha = senha;
	}

	public String getNome_de_usuario() {
		return nome_de_usuario;
	}

	public void setNome_de_usuario(String nome_de_usuario) {
		this.nome_de_usuario = nome_de_usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
