package model.helperView;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.ProjetoDAO;
import model.pojo.Projeto;

@ManagedBean
@ViewScoped
public class ProjetoBean {
	
	private List<Projeto> projetos;

	public ProjetoBean() {
		List<Projeto> listaAux = new ProjetoDAO().listar();
		
		if(listaAux == null){
			this.projetos = new ArrayList<Projeto>();
		}else{
			projetos = listaAux;
		}
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
	
	
//	private String getCurrentDate(){		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		return sdf.format(new Date());
//	}
	
	
}
