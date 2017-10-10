package model.pojo;

public class Notificacao {

	private int projetoID=0;
	private int alunoID=0;	
	private String mensagem = "";
	
	
	public Notificacao() { }
	
	
	public Notificacao(String mensagem) { 
		this.mensagem = mensagem;
	}
	
	
	public Notificacao(String mensagem, int idProjeto, int idAluno) { 
		this.mensagem = mensagem;
		this.projetoID = idProjeto;
		this.alunoID = idAluno;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public int getProjetoID() {
		return projetoID;
	}
	public void setProjetoID(int projetoID) {
		this.projetoID = projetoID;
	}
	public int getAlunoID() {
		return alunoID;
	}
	public void setAlunoID(int alunoID) {
		this.alunoID = alunoID;
	}
}
