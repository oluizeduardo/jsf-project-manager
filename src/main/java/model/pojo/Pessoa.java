package model.pojo;


public class Pessoa {
	
	private String nome;
	private String documentoRG;
	private String documentoCPF;
	private String sexo;
	private String estadoCivil;
	private String dataNascimento;
	private long matricula;	
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private String senha;
	
	
	
	public Pessoa() {}
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumentoRG() {
		return documentoRG;
	}
	public void setDocumentoRG(String documentoRG) {
		this.documentoRG = documentoRG;
	}
	public String getDocumentoCPF() {
		return documentoCPF;
	}
	public void setDocumentoCPF(String documentoCPF) {
		this.documentoCPF = documentoCPF;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public long getMatricula() {
		return matricula;
	}
	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
