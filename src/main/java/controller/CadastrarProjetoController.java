package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Habilidade;
import model.pojo.Projeto;
import view.Mensagem;

@ManagedBean
@ViewScoped
public class CadastrarProjetoController implements Serializable{

	private static final long serialVersionUID = 1L;

	// Guarda os dados do novo projeto que será cadastrado.
	private Projeto projeto = new Projeto();
	// Lista de habilidades exigidas neste projeto.
	private List<Habilidade> habilidades = null;
	// Habilidade selecionada para ser excluida da lista.
	private Habilidade habilidade = new Habilidade(null, null);
	// Acesso à lista de cursos aos quais o novo projeto se destina.
	private List<String> cursosAlvo = new ArrayList<String>();	
	// Curso alvo do projeto.
	private String cursoAlvo = null;
	// Curso alvo do projeto.
	private String cursoSelecionado = null;
	
	
	
	public CadastrarProjetoController() { }

	
	
	
	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	private String getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	
	/**
	 * Envia os dados de um novo projeto para o banco de dados.
	 * É exibido uma mensagem na tela informando o usuário sobre o status da execução.
	 */
	public void salvarProjeto() {
				
		if(projeto.getTitulo().isEmpty()){
			Mensagem.ExibeMensagemAtencao("Informe o título do projeto.");
		}else{
			if(projeto.getDataInicio().isEmpty() || projeto.getDataFim().isEmpty()){
				Mensagem.ExibeMensagemAtencao("Informe corretamente a data de início e fim do projeto.");
			}else{
				if(projeto.getDescricaoCurta().isEmpty()){
					Mensagem.ExibeMensagemAtencao("Descreva este projeto em até 70 caractéres.");
				}else{
					
					projeto.setDataPublicacao(getDataAtual());
					boolean salvou = new ProjetoDAO().salvar(projeto);
					
					if(salvou){
						Mensagem.ExibeMensagem("Novo projeto salvo com sucesso!");
						
						// Reinicia o objeto para limpar os campos da tela.
						this.projeto = new Projeto();
					}else{
						Mensagem.ExibeMensagemErro("Houve um problema ao tentar salvar o novo projeto.");
					}
				}
			}
		}
	}

	
	/**
	 * Retorna uma lista de todos os projetos cadastrados no banco de dados.
	 */
	public ArrayList<Projeto> buscarProjeto() {
		return (ArrayList<Projeto>) (new ProjetoDAO().listar());
	}

	
	public void addHabilidade(){
		
		String desc = habilidade.getDescricao();
		String nivel = habilidade.getNivel();
		
		System.err.println("NOVA HABILIDADE: ("+desc+" - "+nivel+")");
		
		this.habilidades.add(habilidade);
	}
	
	
	/**
	 * Adiciona um novo curso na lista dos cursos alvo do novo projeto.
	 */
	public void addCursoAlvo(){
		if(cursoAlvo != null)
			this.cursosAlvo.add(cursoAlvo);
	}
	
	
	
	public void excluiCursoAlvo(){
		if(cursoSelecionado != null)
			cursosAlvo.remove(cursoSelecionado);
	}

	
	public List<Habilidade> getHabilidades() {
		if(habilidades == null){
			habilidades = new ArrayList<Habilidade>();
			habilidades.add(new Habilidade("Java", "Médio"));
			habilidades.add(new Habilidade("Banco de dados", "Básico"));
			habilidades.add(new Habilidade("Javascript", "Avançado"));
			habilidades.add(new Habilidade("Engenharia de Software", "Básico"));
			habilidades.add(new Habilidade("Python", "Básico"));
		}		
		return habilidades;
	}

	public void exluiHabilidade(){
		this.habilidades.remove(habilidade);
		System.out.println("REMOVIDO: "+habilidade.getDescricao());
	}
	
	
	
	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
	public Habilidade getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}
	public String getCursoAlvo() {
		return cursoAlvo;
	}
	public void setCursoAlvo(String cursoAlvo) {
		this.cursoAlvo = cursoAlvo;
	}
	public List<String> getCursosAlvo() {
		return cursosAlvo;
	}
	public void setCursosAlvo(List<String> cursosAlvo) {
		this.cursosAlvo = cursosAlvo;
	}
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public String getCursoSelecionado() {
		return cursoSelecionado;
	}
	public void setCursoSelecionado(String cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	
}
