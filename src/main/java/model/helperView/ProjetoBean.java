package model.helperView;

import java.text.SimpleDateFormat;
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
		Professor coordenador2 = new Professor("M�rcia Oliveira Dias", Titulacao.DOUTOR);
		
		String dataPublicacao = getCurrentDate();			
		
		projetos = new ArrayList<Projeto>();
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Aplicativo M�vel", "Desenvolver um aplicativo para consulta de notas.", coordenador1, dataPublicacao));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Jogo", "Desenvolver um jogo para crian�as deficientes.", coordenador1, dataPublicacao));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Plano de Neg�cios", "Desenvolvimento de um PN para a empresa junior.", coordenador2, dataPublicacao));
		projetos.add(new Projeto(Projeto.INICIACAO_CIENTIFICA, "Bot�nica", "Estudo sobre as flores da pra�a da UNIV�S.", coordenador2, dataPublicacao));
		
		projetos.add(new Projeto(Projeto.INICIACAO_CIENTIFICA, "Gen�tica", "Estudo sobre gen�tica dos cachorros Pitbull.", coordenador2, dataPublicacao));
		projetos.add(new Projeto(Projeto.TRABALHO_ACADEMICO, "Jogo", "Desenvolver um jogo de perguntas e respostas.", coordenador1, dataPublicacao));
		projetos.add(new Projeto(Projeto.PROJETO_DE_EXTENSAO, "Gin�stica", "Trabalho com idosos.", coordenador2, dataPublicacao));
		projetos.add(new Projeto(Projeto.EVENTO_INTERNO, "Campeonato", "Volunt�rios para o campeonato de futsal.", coordenador2, dataPublicacao));
		
		projetos.add(new Projeto(Projeto.EVENTO_EXTERNO, "Fisioterapia", "Atividade pr�tica na APAE de Pouso Alegre.", coordenador2, dataPublicacao));
		projetos.add(new Projeto(Projeto.PROJETO_DE_EXTENSAO, "Jogo", "Desenvolver um jogo para crian�as deficientes.", coordenador1,  dataPublicacao));
		projetos.add(new Projeto(Projeto.EVENTO_EXTERNO, "Elabora��o de Curr�culos", "Volunt�rios do curso de RH para workshop sobre como montar o curr�culo.", coordenador2, dataPublicacao));
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
	
	
	private String getCurrentDate(){		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}
	
	
}
