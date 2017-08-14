package model;

import java.util.Date;
import java.util.List;

public class Aluno {

	private String nomeCompleto;
	private String documentoRG;
	private String documentoCPF;
	private String sexo;
	private String estadoCivil;
	private Date dataNascimento;
	private String matricula;
	private String curso;
	private String dataMatricula;
	private Endereco endereco;
	private Contato contato;
	private List<Idioma> idiomas;
	private List<Habilidade> habilidades;
	private Acesso acesso;
	
	
	public Aluno() { }


	public String getNome() {
		return nomeCompleto;
	}


	public void setNome(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}


	public String getDocumentoRG() {
		return documentoRG;
	}


	public void setDocumentoRG(String rg) {
		documentoRG = rg;
	}


	public String getDocumentoCPF() {
		return documentoCPF;
	}


	public void setDocumentoCPF(String cpf) {
		documentoCPF = cpf;
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


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}


	public String getDataMatricula() {
		return dataMatricula;
	}


	public void setDataMatricula(String dataMatricula) {
		this.dataMatricula = dataMatricula;
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


	public List<Habilidade> getHabilidades() {
		return habilidades;
	}


	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}


	public Acesso getAcesso() {
		return acesso;
	}


	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}
	
	@Override
	public String toString() {
		return "Nome: "+getNome()+" - CPF: "+getDocumentoCPF()+" - Usuário: "+getAcesso().getUsuario();
	}
	
}
