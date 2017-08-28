package model.pojo;

import java.util.Date;
import java.util.List;

public class Projeto {
	
	private long codigo;// Código/Registro de publicação.
	private String titulo;
	private String descricaoCurta;
	private String resumo;
	private Professor coordenador;
	private List<Curso> cursosEnvolvidos;
	private List<Habilidade> habilidades;	
	private List<Aluno> alunos;
	private String status;//Em andamento, Finalizado, Cancelado, etc.
	private Date dataInicio;
	private Date dataFim;
	private Date dataPublicacao;
	private Financiamento financiamento;
	
	
	
	public Projeto() { }
	
	
	public Projeto(long codigo, String titulo, String descricao, Date dataPublicacao) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricaoCurta = descricao;
		this.dataPublicacao = dataPublicacao;
	}


	public long getCodigo() {
		return codigo;
	}


	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	public String getDescricaoCurta() {
		return descricaoCurta;
	}


	public void setDescricaoCurta(String descricao) {
		this.descricaoCurta = descricao;
	}
	
	public String getResumo() {
		return resumo;
	}


	public void setResumo(String resumo) {
		this.resumo = resumo;
	}


	public Professor getCoordenador() {
		return coordenador;
	}


	public void setCoordenador(Professor coordenador) {
		this.coordenador = coordenador;
	}


	public List<Curso> getCursosEnvolvidos() {
		return cursosEnvolvidos;
	}


	public void setCursosEnvolvidos(List<Curso> cursosEnvolvidos) {
		this.cursosEnvolvidos = cursosEnvolvidos;
	}


	public List<Habilidade> getHabilidades() {
		return habilidades;
	}


	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}


	public List<Aluno> getAlunos() {
		return alunos;
	}


	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public Date getDataPublicacao() {
		return dataPublicacao;
	}


	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}


	public Financiamento getFinanciamento() {
		return financiamento;
	}


	public void setFinanciamento(Financiamento financiamento) {
		this.financiamento = financiamento;
	}

	
	
	
}
