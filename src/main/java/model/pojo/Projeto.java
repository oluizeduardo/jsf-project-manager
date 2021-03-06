package model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;


/**
 * Classe que modela um projeto criado por um professor.
 * 
 * @author Luiz Eduardo
 */
@ManagedBean 
public class Projeto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Categorias de projeto.
	public static final String PROJETO_DE_EXTENSAO = "Projeto de Extens�o";
	public static final String INICIACAO_CIENTIFICA = "Inicia��o Cient�fica";
	public static final String ESTAGIO = "Est�gio";
	public static final String EVENTO_INTERNO = "Evento Interno";
	public static final String EVENTO_EXTERNO = "Evento Externo";
	public static final String TRABALHO_ACADEMICO = "Trabalho Acad�mico";
	
	// Poss�veis status do projeto.
	public static final String FINALIZADO = "Finalizado";
	public static final String EM_EXECUCAO = "Em Execu��o";
	public static final String CANCELADO = "Cancelado";
	public static final String AGUARDANDO = "Aguardando In�cio";
	
	public List<String> listaDeStatus = null;
	
	// Identifica��o
	private String categoria = TRABALHO_ACADEMICO;// Item default no cadastro de novo projeto.
	private String titulo="";
	private String descricaoCurta="";
	private String dataInicio="";
	private String dataFim="";
	private String dataPublicacao="";
	
	// Administra��o
	private Pessoa coordenador = new Professor();
	private Financiamento financiamento = new Financiamento();
	
	// Cursos e Habilidades
	private List<Curso> cursosEnvolvidos = new ArrayList<Curso>();
	private List<Habilidade> habilidades = new ArrayList<Habilidade>();
	private Integer numeroDeParticipantes;
	
	// Resumo
	private String resumo="";
	
	// Equipe
	private List<Aluno> alunos = null;

	// Status do Projeto
	private String status="";//Em andamento, Finalizado, Cancelado, etc.
	
	// Lista de poss�veis categorias de um projeto.
	private List<String> categorias = new ArrayList<String>();
	
	// Nome do �cone que aparecer� no card do projeto na home do aluno.
	private String nomeIcone="";
	
	// C�digo identificador do projeto no banco de dados.
	private int ID;
	
	
	public Projeto() { }
	
	
	
	public Projeto(String categoria, String titulo, String descricao, Professor coordenador, String dataPublicacao) {
		this.categoria = categoria;
		this.titulo = titulo;
		this.descricaoCurta = descricao;
		this.coordenador = coordenador;
		this.dataPublicacao = dataPublicacao;
	}



	
	/**
	 * Retorna uma lista com as poss�veis categorias de um projeto.
	 */
	public List<String> getCategorias() {
		categorias.add(ESTAGIO);
		categorias.add(EVENTO_EXTERNO);
		categorias.add(EVENTO_INTERNO);
		categorias.add(INICIACAO_CIENTIFICA);
		categorias.add(PROJETO_DE_EXTENSAO);
		categorias.add(TRABALHO_ACADEMICO);
		
		return categorias;
	}

	/**
	 * Define uma nova lista de poss�veis naturezas de um projeto.
	 */
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	
	/**
	 * Retorna uma lista com os poss�veis status de um projeto.
	 */
	public List<String> getListaDeStatus() {
		this.listaDeStatus = new ArrayList<String>();
		listaDeStatus.add(AGUARDANDO);
		listaDeStatus.add(EM_EXECUCAO);
		listaDeStatus.add(FINALIZADO);
		return listaDeStatus;
	}
	
	void setListaDeStatus(List<String> novaListaDeStatus) {
		this.listaDeStatus = novaListaDeStatus;
	}
	
	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
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


	public Pessoa getCoordenador() {
		return coordenador;
	}
	public void setCoordenador(Pessoa coordenador) {
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

	public Integer getNumeroDeParticipantes() {
		return numeroDeParticipantes;
	}
	public void setNumeroDeParticipantes(Integer numeroDeParticipantes) {
		this.numeroDeParticipantes = numeroDeParticipantes;
	}
	public String getNomeIcone() {
		return nomeIcone;
	}
	public void setNomeIcone(String nomeIcone) {
		this.nomeIcone = nomeIcone;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
}
