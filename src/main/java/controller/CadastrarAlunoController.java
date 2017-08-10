package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import model.Aluno;


@ManagedBean
@ViewScoped
public class CadastrarAlunoController {

	
	private Aluno aluno = new Aluno();
	
	
	public String onFlowProcess(FlowEvent event) {
		
		return event.getNewStep();
	}


	public Aluno getAluno() {
		return aluno;
	}


	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	
	
	
}
