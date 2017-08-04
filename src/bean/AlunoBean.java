package bean;

import javax.faces.bean.ManagedBean;

import pojo.Aluno;

@ManagedBean
public class AlunoBean {

	private Aluno alunoBean  = new Aluno();

	
	public Aluno getAluno(){
		return alunoBean;
	}

	public void add() {
		System.out.println("Aluno adicionado!!");
	}
	
}
