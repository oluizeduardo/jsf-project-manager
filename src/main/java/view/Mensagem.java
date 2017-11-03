package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;


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
		if(facesContext != null)
			facesContext.addMessage(null, new FacesMessage("",mensagem));
	}

	/**
	 * Exibe uma mensagem de aten��o na tela onde � chamado.
	 */
	public static void ExibeMensagemAtencao(String mensagem){		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if(facesContext != null)
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o:", mensagem));
	}
	
	/**
	 * Exibe uma mensagem de informa��o na tela onde � chamado.
	 */
	public static void ExibeMensagemErro(String mensagem){		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if(facesContext != null)
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensagem));
	}
	
	/**
	 * Exibe uma mensagem de informa��o no formato de tela Dialog.
	 */
	public static void ExibeMensagemDialog(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem);         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
