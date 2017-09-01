package model.helperView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Professor;
import model.pojo.Projeto;
import model.pojo.Titulacao;

@ManagedBean
@ViewScoped
public class ProjetoBean {
	
	private List<Projeto> projetos;

	public ProjetoBean() {
		
		Professor coordenador1 = new Professor("Roberto Ribeiro Rocha", Titulacao.MESTRE);
		Professor coordenador2 = new Professor("Márcia Oliveira Dias", Titulacao.DOUTOR);
		
		projetos = new ArrayList<Projeto>();
		projetos.add(new Projeto(1234, "Aplicativo Móvel", "Desenvolver um aplicativo para consulta de notas.", coordenador1, new Date()));
		projetos.add(new Projeto(3456, "Jogo", "Desenvolver um jogo para crianças deficientes.", coordenador1, new Date()));
		projetos.add(new Projeto(8766, "Plano de Negócios", "Desenvolvimento de um PN para a empresa junior.", coordenador2, new Date()));
		projetos.add(new Projeto(3657, "Botânica", "Estudo sobre as flores da praça da UNIVÁS.", coordenador2, new Date()));
		
		projetos.add(new Projeto(8674, "Genética", "Estudo sobre genética dos cachorros Pitbull.", coordenador2, new Date()));
		projetos.add(new Projeto(7564, "Jogo", "Desenvolver um jogo de perguntas e respostas.", coordenador1, new Date()));
		projetos.add(new Projeto(4760, "Ginástica", "Trabalho com idosos.", coordenador2, new Date()));
		projetos.add(new Projeto(2650, "Campeonato", "Voluntários para o campeonato de futsal.", coordenador2, new Date()));
		
		projetos.add(new Projeto(7756, "Fisioterapia", "Atividade prática na APAE de Pouso Alegre.", coordenador2, new Date()));
		projetos.add(new Projeto(9811, "Jogo", "Desenvolver um jogo para crianças deficientes.", coordenador1,  new Date()));
		projetos.add(new Projeto(3387, "Elaboração de Currículos", "Voluntários do curso de RH para workshop sobre como montar o currículo.", coordenador2, new Date()));
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
}
