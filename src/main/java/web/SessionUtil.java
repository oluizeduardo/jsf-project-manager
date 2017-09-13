package web;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// http://respostas.guj.com.br/7684-como-enviar-parametros-de-uma-pagina-para-outra
/**
 * Classe criada para gerenciar as sessões de login no sistema.
 */
public class SessionUtil {

	
	/**Nome da chave utilizada para associar com um valor na Session.*/
	public static final String KEY_SESSION = "user-logged"; 
	
	
	/**
	 * Retorna uma instância da sessão.
	 */
    private static HttpSession getSession() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) ctx.getExternalContext().getSession(true);
        return sessao;
    }

    
    /**
     * Define parâmetros para a sessão.
     * @param key
     * @param value
     */
    public static void setParam(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * Retorna o valor de determinada chave de uma sessão.
     */
    public static Object getParam(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * Remove o valor de um atributo da sessão atual.
     * @param key
     */
    public static void remove(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * Destrói a sessão atual, logout.
     */
    public static void invalidate() {
        getSession().invalidate();
    }
}