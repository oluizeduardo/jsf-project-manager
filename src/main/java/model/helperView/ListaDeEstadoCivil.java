package model.helperView;

import java.util.ArrayList;
import java.util.List;

public class ListaDeEstadoCivil {

	
	private List<String> list;

	
	
	public ListaDeEstadoCivil() {

		list = new ArrayList<String>();

		list.add("Casado(a)");
		list.add("Divorciado(a)");		
		list.add("Separado(a)");
		list.add("Solteiro(a)");
		list.add("Viúvo(a)");
	}

	
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	
}
