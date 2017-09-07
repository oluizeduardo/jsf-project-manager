package model.helperView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.DragDropEvent;

import model.pojo.Professor;
import model.pojo.Projeto;
import model.pojo.Titulacao;

/**
 * Drag and Drop helper.
 *
 */
@ManagedBean(name = "dndProjectsView")
@ViewScoped
public class DNDProjectsView {
 
	/**Lista de projetos para alimentar a lista de Drag n' Drop.*/
    private List<Projeto> projetos;
    
    /**Lista de projetos adicionados no Drag n' Drop.*/
    private List<Projeto> projetosSelecionados;
     
    private Projeto projetoSelecionado;
     
    
    
    @PostConstruct
    public void init() {
        carregaListaDeProjetos();
        projetosSelecionados = new ArrayList<Projeto>();
    }
     
    
    public void onProjectDrop(DragDropEvent ddEvent) {
        Projeto projeto = ((Projeto) ddEvent.getData());
  
        projetosSelecionados.add(projeto);
        projetos.remove(projeto);
    }
 
    public List<Projeto> getProjetos() {
        return projetos;
    }
 
    public List<Projeto> getProjetosSelecionados() {
        return projetosSelecionados;
    }    
 
    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }
 
    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }
	
    
    private void carregaListaDeProjetos(){
    	Professor coordenador1 = new Professor("Roberto Ribeiro Rocha", Titulacao.MESTRE);
    	Professor coordenador2 = new Professor("Márcia Oliveira Dias", Titulacao.DOUTOR);
    	
    	String dataPublicacao = getCurrentDate();			
		
    	projetos = new ArrayList<Projeto>();
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Aplicativo Móvel", "Desenvolver um aplicativo para consulta de notas.", coordenador1, new Date()));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Jogo", "Desenvolver um jogo para crianças deficientes.", coordenador1, new Date()));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Plano de Negócios", "Desenvolvimento de um PN para a empresa junior.", coordenador2, new Date()));
		projetos.add(new Projeto(Projeto.INICIACAO_CIENTIFICA, "Botânica", "Estudo sobre as flores da praça da UNIVÁS.", coordenador2, new Date()));
		
		projetos.add(new Projeto(Projeto.INICIACAO_CIENTIFICA, "Genética", "Estudo sobre genética dos cachorros Pitbull.", coordenador2, new Date()));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Jogo", "Desenvolver um jogo de perguntas e respostas.", coordenador1, new Date()));
		projetos.add(new Projeto(Projeto.PROJETO_DE_EXTENSAO, "Ginástica", "Trabalho com idosos.", coordenador2, new Date()));
		projetos.add(new Projeto(Projeto.EVENTO_INTERNO, "Campeonato", "Voluntários para o campeonato de futsal.", coordenador2, new Date()));
		
		projetos.add(new Projeto(Projeto.EVENTO_EXTERNO, "Fisioterapia", "Atividade prática na APAE de Pouso Alegre.", coordenador2, new Date()));
		projetos.add(new Projeto(Projeto.PROJETO_DE_EXTENSAO, "Jogo", "Desenvolver um jogo para crianças deficientes.", coordenador1,  new Date()));
		projetos.add(new Projeto(Projeto.EVENTO_EXTERNO, "Elaboração de Currículos", "Voluntários do curso de RH para workshop sobre como montar o currículo.", coordenador2, new Date()));
    }
	
    
    private String getCurrentDate(){		
		SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/yyyy");
		return sdf.format(new Date());
	}
    
}
