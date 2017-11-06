package model.pojo;

public class Financiamento {

	
	public static final String POSSUI = "Possui financiamento";
	public static final String NAO_POSSUI = "Não possui financiamento";
	
	
	// O projeto possui financiamento?
	private boolean existente = false;
	
	// Auxílio, bolsa, etc.
	private String natureza;
	
	// O valor do auxílio, bolsa.
	private Float valor;
	
	
	public Financiamento() { }
	
	
	public Financiamento(boolean existente, String natureza, Float valor) {
		this.existente = existente;
		this.natureza = natureza;
		this.valor = valor;
	}


	public boolean isExistente() {
//		if(valor != null)
//			existente = valor > 0 ? true : false;
		return existente;
	}


	public void setExistente(boolean existente) {
		this.existente = existente;
	}


	public String getNatureza() {
		return natureza;
	}


	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}


	public Float getValor() {
		return valor;
	}


	public void setValor(Float valor) {
		this.valor = valor;
	}
	
}
