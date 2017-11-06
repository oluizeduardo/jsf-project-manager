package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Projeto;
import view.Mensagem;


@ManagedBean
@ViewScoped
public class EditarProjetoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Projeto selecionado para edição.
	private Projeto projeto = new Projeto();
	// Curso para o qual o projeto editado se destina.
	private String cursoAlvo = null;
	// Lista de cursos para os quais o projeto editado se destina.
	//private List<Curso> cursosAlvo = new ArrayList<Curso>();
	// Curso selecionado para ser excluído.
	private Curso cursoSelecionado = new Curso();
	// Nova habilidade.
	private Habilidade habilidade = new Habilidade();
	
	
	
	public EditarProjetoController() {	}

	
	
	/**
	 * Invoca o método responsável por atualizar os dados do projeto
	 * no banco de dados.
	 */
	public void salvarEdicao(){			
		boolean atualizou = new ProjetoDAO().atualizar(projeto);
		if(atualizou){
			Mensagem.ExibeMensagem("Alterações salvas com sucesso!!");
		}else{
			Mensagem.ExibeMensagemErro("Não foi possível salvar as alterações!!");
		}
	}
	
	
	
	
//	/**
//	 * Adiciona um novo curso na lista de cursos alvo.
//	 */
//	public void addHabilidade(){
//		System.out.println("Adicionando nova habilidade: "+habilidade.getDescricao());
//	}
//	
//	/**
//	 * Adiciona um novo curso na lista de cursos alvo.
//	 */
//	public void addCursoAlvo(){
//		for (int i =0; i < projeto.getCursosEnvolvidos().size(); i++) {
//			Curso c = projeto.getCursosEnvolvidos().get( i );
//			if(!c.getNome().equals(cursoAlvo)){
//				projeto.getCursosEnvolvidos().add(new Curso(cursoAlvo));
//			}
//		}	
//	}
//	
//	/**
//	 * Remove um curso da lista de cursos alvo.
//	 */
//	public void excluiCursoAlvo(){
//		this.projeto.getCursosEnvolvidos().remove(cursoSelecionado);
//	}
	
	
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public String getCursoAlvo() {
		return cursoAlvo;
	}
	public void setCursoAlvo(String cursoAlvo) {
		this.cursoAlvo = cursoAlvo;
	}
	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}
	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	public Habilidade getHabilidade() {
		return habilidade;
	}
	public void setHabilidade(Habilidade habilidade) {
		this.habilidade = habilidade;
	}
	
	
	
	
}
