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
		projetos.add(new Projeto("Aplicativo M�vel", "Desenvolver um aplicativo para consulta de notas.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo para crian�as deficientes.", new Date()));
		projetos.add(new Projeto("Plano de Neg�cios", "Desenvolvimento de um PN para a empresa junior.", new Date()));
		projetos.add(new Projeto("Bot�nica", "Estudo sobre as flores da pra�a da UNIV�S.", new Date()));
		
		projetos.add(new Projeto("Gen�tica", "Estudo sobre gen�tica dos cachorros Pitbull.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo de perguntas e respostas.", new Date()));
		projetos.add(new Projeto("Gin�stica", "Trabalho com idosos.", new Date()));
		projetos.add(new Projeto("Campeonato", "Volunt�rios para o campeonato de futsal.", new Date()));
		
		projetos.add(new Projeto("Fisioterapia", "Atividade pr�tica na APAE de Pouso Alegre.", new Date()));
		projetos.add(new Projeto("Jogo", "Desenvolver um jogo para crian�as deficientes.", new Date()));
		projetos.add(new Projeto("Elabora��o de Curr�culos", "Volunt�rios do curso de RH para workshop sobre como montar o curr�culo.", new Date()));
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
}
