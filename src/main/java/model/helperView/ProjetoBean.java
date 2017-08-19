package model.helperView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.pojo.Projeto;

@ManagedBean
@ViewScoped
public class ProjetoBean {
	
	private List<Projeto> projetos;

	public ProjetoBean() {
		projetos = new ArrayList<Projeto>();
		projetos.add(new Projeto("Aplicativo Móvel", "Desenvolver um aplicativo para consulta de notas.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo para crianças deficientes.", new Date()));
		projetos.add(new Projeto("Plano de Negócios", "Desenvolvimento de um PN para a empresa junior.", new Date()));
		projetos.add(new Projeto("Botânica", "Estudo sobre as flores da praça da UNIVÁS.", new Date()));
		
		projetos.add(new Projeto("Genética", "Estudo sobre genética dos cachorros Pitbull.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo de perguntas e respostas.", new Date()));
		projetos.add(new Projeto("Ginástica", "Trabalho com idosos.", new Date()));
		projetos.add(new Projeto("Campeonato", "Voluntários para o campeonato de futsal.", new Date()));
		
		projetos.add(new Projeto("Fisioterapia", "Atividade prática na APAE de Pouso Alegre.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo para crianças deficientes.", new Date()));
		projetos.add(new Projeto("Elaboração de Currículos", "Voluntários do curso de RH para workshop sobre como montar o currículo.", new Date()));
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
}
