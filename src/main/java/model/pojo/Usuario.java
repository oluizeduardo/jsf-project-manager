package model.pojo;

public class Usuario extends Pessoa {

	private String email;
	private String senha;
	private boolean lembrarSenha = false;
	private boolean ativo = false;
	

	public Usuario() {	}
	
	
	
	public Usuario(String email, String senha, boolean ativo){
		this.email = email;
		this.senha = senha;
		this.ativo = ativo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	
	
}
