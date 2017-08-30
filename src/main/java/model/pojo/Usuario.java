package model.pojo;

public class Usuario extends Pessoa {

	private String senha;
	private boolean ativo = false;
	

	public Usuario() {	}
	
	
	
	public Usuario(String email, String senha, boolean ativo){
		super.getContato().setEmail(email);
		this.senha = senha;
		this.ativo = ativo;
	}
	


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	
	
}
