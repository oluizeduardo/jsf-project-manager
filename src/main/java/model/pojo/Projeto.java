package model.pojo;

import java.util.List;


public class Projeto {
	
	// Identificação
	private long codigo;
	private String titulo;
	private String descricaoCurta;
	private String dataInicio;
	private String dataFim;
	private String dataPublicacao;
	
	// Administração
	private Professor coordenador;
	private Financiamento financiamento;
	
	// Cursos e Habilidades
	private List<Curso> cursosEnvolvidos;
	private List<Habilidade> habilidades;
	private int numeroDeParticipantes;
	
	// Resumo
	private String resumo;
	
	// Equipe
	private List<Aluno> alunos;
	
	// Status do Projeto
	private String status;//Em andamento, Finalizado, Cancelado, etc.
	
	
	
	
	
	public Projeto() { }
	
	
	
	
	public Projeto(long codigo, String titulo, String descricao, Professor coordenador, String dataPublicacao) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricaoCurta = descricao;
		this.coordenador = coordenador;
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


	public String getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}


	public String getDataFim() {
		return dataFim;
	}


	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}


	public String getDataPublicacao() {
		return dataPublicacao;
	}


	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}


	public Financiamento getFinanciamento() {
		return financiamento;
	}


	public void setFinanciamento(Financiamento financiamento) {
		this.financiamento = financiamento;
	}

	public int getNumeroDeParticipantes() {
		return numeroDeParticipantes;
	}

	public void setNumeroDeParticipantes(int numeroDeParticipantes) {
		this.numeroDeParticipantes = numeroDeParticipantes;
	}

	
	
}
