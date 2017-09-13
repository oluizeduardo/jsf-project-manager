package web;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// http://respostas.guj.com.br/7684-como-enviar-parametros-de-uma-pagina-para-outra
/**
 * Classe criada para gerenciar as sess�es de login no sistema.
 */
public class SessionUtil {

	
	/**Nome da chave utilizada para associar com um valor na Session.*/
	public static final String KEY_SESSION = "user-logged"; 
	
	
	/**
	 * Retorna uma inst�ncia da sess�o.
	 */
    private static HttpSession getSession() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) ctx.getExternalContext().getSession(true);
        return sessao;
    }

    
    /**
     * Define par�metros para a sess�o.
     * @param key
     * @param value
     */
    public static void setParam(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * Retorna o valor de determinada chave de uma sess�o.
     */
    public static Object getParam(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * Remove o valor de um atributo da sess�o atual.
     * @param key
     */
    public static void remove(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * Destr�i a sess�o atual, logout.
     */
    public static void invalidate() {
        getSession().invalidate();
    }
}