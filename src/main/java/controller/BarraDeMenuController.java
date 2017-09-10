package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import web.SessionUtil;

@ManagedBean(name = "barraDeMenu")
@ViewScoped
public class BarraDeMenuController implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Este método executa o logout do usuário no sistema.
	 */
	public void logout(){
		System.out.println("LOGOUT => "+SessionUtil.getParam(SessionUtil.KEY_SESSION));
		SessionUtil.invalidate();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
