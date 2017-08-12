package model.helperView;

import java.util.ArrayList;
import java.util.List;


public class ListaDeEstados {

	
	
	private List<String> list;

	
	
	public ListaDeEstados() {

		list = new ArrayList<String>();

		list.add("Acre	AC");
		list.add("Alagoas	AL");
		list.add("Amap�	AP");
		list.add("Amazonas	AM");
		list.add("Bahia	BA");
		list.add("Cear�	CE");
		list.add("Distrito Federal	DF");
		list.add("Esp�rito Santo	ES");
		list.add("Goi�s	GO");
		list.add("Maranh�o	MA");
		list.add("Mato Grosso	MT");
		list.add("Mato Grosso do Sul	MS");
		list.add("Minas Gerais	MG");
		list.add("Par�	PA");
		list.add("Para�ba	PB");
		list.add("Paran�	PR");
		list.add("Pernambuco	PE");
		list.add("Piau�	PI");
		list.add("Rio de Janeiro	RJ");
		list.add("Rio Grande do Norte	RN");
		list.add("Rio Grande do Sul	RS");
		list.add("Rond�nia	RO");
		list.add("Roraima	RR");
		list.add("Santa Catarina	SC");
		list.add("S�o Paulo	SP");
		list.add("Sergipe	SE");
		list.add("Tocantins   TO");
	}

	
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}


}
