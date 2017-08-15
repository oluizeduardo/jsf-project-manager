package controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FlowEvent;
import model.pojo.Aluno;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;
import model.helperView.ListaDeIdiomas;


@ManagedBean
@ViewScoped
public class CadastrarAlunoController {

	
	private Aluno aluno = null;
	private List<String> estadosBrasileiros = null;
	private List<String> estadoCivil = null;
	private List<String> idiomas = null;
	
	
	
	public CadastrarAlunoController() {
		this.aluno = new Aluno();
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
		this.idiomas = new ListaDeIdiomas().getList();
	}
	
	
	
	
	public String onFlowProcess(FlowEvent event) {

		return event.getNewStep();
	}


	
	public void salvar(){
		
		System.out.println("Dados do aluno salvos com sucesso!");
		
	}
	
	
	
	
	public Aluno getAluno() {
		return aluno;
	}


	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	
	public List<String> getEstadosBrasileiros() {
		return estadosBrasileiros;
	}
	
	public List<String> getEstadoCivil() {
		return estadoCivil;
	}
	
	public List<String> getIdiomas() {
		return idiomas;
	}
	
	
}
