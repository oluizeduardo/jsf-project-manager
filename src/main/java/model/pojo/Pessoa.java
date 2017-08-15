package model.pojo;

import java.util.Date;
import java.util.List;

public class Pessoa {
	
	private String nome;
	private String documentoRG;
	private String documentoCPF;
	private String sexo;
	private String estadoCivil;
	private Date dataNascimento;
	private String matricula;	
	private Endereco endereco;
	private Contato contato;
	private List<Idioma> idiomas;
	
	
	
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
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
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
	public List<Idioma> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(List<Idioma> idiomas) {
		this.idiomas = idiomas;
	}

}
