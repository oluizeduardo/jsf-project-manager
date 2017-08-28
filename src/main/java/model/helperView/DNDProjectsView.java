package model.helperView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.DragDropEvent;
import model.pojo.Projeto;

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
    	projetos = new ArrayList<Projeto>();
    	
    	projetos.add(new Projeto(1234, "Aplicativo M�vel", "Desenvolver um aplicativo para consulta de notas.", new Date()));
		projetos.add(new Projeto(3456, "Jogo", "Desenvolver um jogo para crian�as deficientes.", new Date()));
		projetos.add(new Projeto(8766, "Plano de Neg�cios", "Desenvolvimento de um PN para a empresa junior.", new Date()));
		projetos.add(new Projeto(3657, "Bot�nica", "Estudo sobre as flores da pra�a da UNIV�S.", new Date()));
		
		projetos.add(new Projeto(8674, "Gen�tica", "Estudo sobre gen�tica dos cachorros Pitbull.", new Date()));
		projetos.add(new Projeto(7564, "Jogo", "Desenvolver um jogo de perguntas e respostas.", new Date()));
		projetos.add(new Projeto(4760, "Gin�stica", "Trabalho com idosos.", new Date()));
		projetos.add(new Projeto(2650, "Campeonato", "Volunt�rios para o campeonato de futsal.", new Date()));
		
		projetos.add(new Projeto(7756, "Fisioterapia", "Atividade pr�tica na APAE de Pouso Alegre.", new Date()));
		projetos.add(new Projeto(9811, "Jogo", "Desenvolver um jogo para crian�as deficientes.", new Date()));
		projetos.add(new Projeto(3387, "Elabora��o de Curr�culos", "Volunt�rios do curso de RH para workshop sobre como montar o curr�culo.", new Date()));
    }
	
}
