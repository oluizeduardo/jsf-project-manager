package model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import com.lowagie.text.Document;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import model.pojo.Pessoa;
import web.SessionUtil;


@ManagedBean
public class FormatadorPDF implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public FormatadorPDF() { }
	
	
	public void preProcessPDF(Object document){
        
        Document pdf = (Document) document; 
        
        String anoAtual = CalendarioFormatado.getAnoAtual();
        String dataAtual = CalendarioFormatado.getDataAtual();
        
        String phraseHeader = "Lista de alunos indicados para projetos\n "
        		+ "Professor: "+getNomeProfessorLogado()+"\n"
        		+ "Gerado em: "+dataAtual;
        HeaderFooter header = new HeaderFooter(new Phrase(phraseHeader), false);
 
        HeaderFooter footer = new HeaderFooter(new Phrase("UNIVÁS - Sistemas de Informação - "+anoAtual), false);
        footer.setAlignment(HeaderFooter.ALIGN_CENTER);
        
        pdf.setHeader(header);
        pdf.setFooter(footer);
        pdf.open();
    }
	
	
	
	/**
	 * Retorna o nome do professor logado no sistema.
	 */
	private String getNomeProfessorLogado(){
		Pessoa professor = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		return professor.getNome();
	}
	
}
