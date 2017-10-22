package model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import com.lowagie.text.Document;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import model.pojo.Pessoa;
import web.SessionUtil;


/**
 * Classe responsável por gerar os arquivos PDF.
 * 
 * @author Luiz Eduardo da Costa
 */
@ManagedBean
public class FormatadorPDF implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public FormatadorPDF() { }
	
	
	
	
	
	/**
	 * Cria o arquivo PDF com a lista de projetos recomendados a um aluno.
	 */
	public void criaPDFProjetosRecomendados(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Projetos recomendados para o aluno "+getNomePessoaLogada()
        		+ "\nGerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	
	/**
	 * Cria o arquivo PDF com a lista de projetos que o aluno participa.
	 */
	public void criaPDFMeusProjetos(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Lista de projetos que o aluno participa "
        		+ "\nAluno: "+getNomePessoaLogada()
        		+ "\nGerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	
	/**
	 * Cria o arquivo PDF com a lista de todos os professores cadastrados no sistema.
	 */
	public void criaPDFProfessoresCadastrados(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Lista de professores cadastrados\n "
        		+ "Gerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	
	/**
	 * Cria o arquivo PDF com a lista de todos os projetos cadastrados no sistema.
	 */
	public void criaPDFProjetosCadastrados(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Lista de projetos cadastrados\n "
        		+ "Gerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	
	/**
	 * Cria o arquivo PDF com a lista de todos os alunos cadastrados no sistema.
	 */
	public void criaPDFAlunosCadastrados(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Lista de alunos cadastrados\n "
        		+ "Gerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	/**
	 * Cria o arquivo PDF dos alunos indicados para projetos do professor.
	 */
	public void criaPDFAlunosIndicados(Object document){
        
        Document pdf = (Document) document; 
                
        String phraseHeader = "Lista de alunos indicados para projetos\n "
        		+ "Professor: "+getNomePessoaLogada()+"\n"
        		+ "Gerado em: "+CalendarioFormatado.getDataHoraAtual();
        
        pdf.setHeader(createHeader(phraseHeader));
        pdf.setFooter(createFooter());
        pdf.open();
    }
	
	
	
	/**
	 * Cria um cabeçalho para o arquivo criado.
	 * 
	 * @param phraseHeader A frase que irá no cabeçalho.
	 * @return Objeto HeaderFooter
	 */
	private HeaderFooter createHeader(String phraseHeader) {		
        HeaderFooter header = new HeaderFooter(new Phrase(phraseHeader), false);
        return header;
	}


	/**
	 * Cria um rodapé padrão para o arquivo criado.
	 * @return Objeto HeaderFooter
	 */
	private HeaderFooter createFooter() {
		String anoAtual = CalendarioFormatado.getAnoAtual();
		HeaderFooter footer = new HeaderFooter(new Phrase("UNIVÁS - Sistemas de Informação - "+anoAtual), false);
        footer.setAlignment(HeaderFooter.ALIGN_CENTER);
        return footer;
	}


	/**
	 * Retorna o nome da pessoa logada no sistema.
	 */
	private String getNomePessoaLogada(){
		Pessoa professor = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		return professor.getNome();
	}
	
}
