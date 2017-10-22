package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarioFormatado {

	
	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	public static String getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}
	
	
	
	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	public static String getDataHoraAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
		return sdf.format(new Date());
	}
	
	
	/**
	 * Retorna o ano do calendário atual.
	 */
	public static String getAnoAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}
	
}
