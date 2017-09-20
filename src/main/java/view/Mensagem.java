package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class Mensagem {

	
	/*
	 * https://github.com/rrocharoberto/psi/blob/master/geps/src/main/java/br/edu/univas/uteis/Uteis.java
	 * 
	 * */
	
	
	
	/**
	 * Exibe uma mensagem de alerta na tela onde � chamado.
	 */
	public static void ExibeMensagem(String mensagem){		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage("",mensagem));
	}

	/**
	 * Exibe uma mensagem de aten��o na tela onde � chamado.
	 */
	public static void ExibeMensagemAtencao(String mensagem){		
		FacesContext facesContext = FacesContext.getCurrentInstance();		
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o:", mensagem));
	}
	
	/**
	 * Exibe uma mensagem de informa��o na tela onde � chamado.
	 */
	public static void ExibeMensagemErro(String mensagem){		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensagem));
	}
	
}
