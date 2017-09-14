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

	// Guarda os dados do novo projeto que ser� cadastrado.
	private Projeto projeto = new Projeto();
	
	// Lista de habilidades exigidas neste projeto.
	private List<Habilidade> habilidades = null;
	
	// Habilidade selecionada para ser excluida da lista.
	private Habilidade habilidade = new Habilidade(null, null);
	
	// Data m�nima para o in�cio do projeto.
	private Date dataMinima = new Date();
	
	
	
	public CadastrarProjetoController() { }

	
	/**
	 * Retorna a data atual formatada para o padr�o de leitura simples.
	 */
	private String getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	
	/**
	 * Envia os dados de um novo projeto para o banco de dados.
	 * � exibido uma mensagem na tela informando o usu�rio sobre o status da execu��o.
	 */
	public void salvarProjeto() {
				
		if(projeto.getTitulo().isEmpty()){
			Mensagem.ExibeMensagemAtencao("Informe o t�tulo do projeto.");
		}else{
			if(projeto.getDataInicio().isEmpty() || projeto.getDataFim().isEmpty()){
				Mensagem.ExibeMensagemAtencao("Informe corretamente a data de in�cio e fim do projeto.");
			}else{
				if(projeto.getDescricaoCurta().isEmpty()){
					Mensagem.ExibeMensagemAtencao("Descreva este projeto em at� 70 caract�res.");
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
	
	

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Habilidade> getHabilidades() {
		if(habilidades == null){
			habilidades = new ArrayList<Habilidade>();
			habilidades.add(new Habilidade("Java", "M�dio"));
			habilidades.add(new Habilidade("Banco de dados", "B�sico"));
			habilidades.add(new Habilidade("Javascript", "Avan�ado"));
			habilidades.add(new Habilidade("Engenharia de Software", "B�sico"));
			habilidades.add(new Habilidade("Python", "B�sico"));
		}		
		return habilidades;
	}

	public void exluirHabilidade(){
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
	public Date getDataMinima() {
		return dataMinima;
	}
	public void setDataMinima(Date dataMinima) {
		this.dataMinima = dataMinima;
	}

}
