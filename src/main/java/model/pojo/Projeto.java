package model.pojo;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Value;


public class Projeto {
	
	// Naturezas de projeto.
	public static final String PROJETO_DE_EXTENSAO = "Projeto de Extensão";
	public static final String INICIACAO_CIENTIFICA = "Iniciação Científica";
	public static final String ESTAGIO = "Estágio";
	public static final String EVENTO_INTERNO = "Evento Interno";
	public static final String EVENTO_EXTERNO = "Evento Externo";
	public static final String TRABALHO_ACADEMICO = "Trabalho Acadêmico";
	
	
	// Identificação
	private String natureza = TRABALHO_ACADEMICO;// Item default no cadastro de novo projeto.
	private String titulo;
	private String descricaoCurta;
	private String dataInicio;
	private String dataFim;
	private String dataPublicacao;
	
	// Administração
	private Professor coordenador = new Professor();
	private Financiamento financiamento = new Financiamento();
	
	// Cursos e Habilidades
	private List<String> cursosEnvolvidos;
	private List<Habilidade> habilidades;
	private int numeroDeParticipantes;
	
	// Resumo
	private String resumo;
	
	// Equipe
	private List<Aluno> alunos;
	
	// Status do Projeto
	private String status;//Em andamento, Finalizado, Cancelado, etc.
	
	// Lista de possíveis naturezas de um projeto.
	private List<String> naturezas;
	
	public Projeto() { }
	
	public Projeto(String natureza, String titulo, String descricao, Professor coordenador, String dataPublicacao) {
		this.natureza = natureza;
		this.titulo = titulo;
		this.descricaoCurta = descricao;
		this.coordenador = coordenador;
		this.dataPublicacao = dataPublicacao;
	}


	/**
	 * Retorna uma lista com as possíveis naturezas de um projeto.
	 */
	public List<String> getNaturezas() {
		// Se o objeto estiver nulo, carrega a lista.
		if(naturezas == null){
			naturezas = new ArrayList<String>();
			naturezas.add(ESTAGIO);
			naturezas.add(EVENTO_EXTERNO);
			naturezas.add(EVENTO_INTERNO);
			naturezas.add(INICIACAO_CIENTIFICA);
			naturezas.add(PROJETO_DE_EXTENSAO);
			naturezas.add(TRABALHO_ACADEMICO);
		}		
		return naturezas;
	}

	/**
	 * Define uma nova lista de possíveis naturezas de um projeto.
	 */
	public void setNaturezas(List<String> naturezas) {
		this.naturezas = naturezas;
	}
	
	public String getNatureza() {
		return natureza;
	}


	public void setNatureza(String natureza) {
		this.natureza = natureza;
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


	public List<String> getCursosEnvolvidos() {
		return cursosEnvolvidos;
	}


	public void setCursosEnvolvidos(List<String> cursosEnvolvidos) {
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
