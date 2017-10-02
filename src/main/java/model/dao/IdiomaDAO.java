package model.dao;

import java.util.List;
import model.pojo.Idioma;


public class IdiomaDAO extends DAOBase {


	public boolean salvar(Idioma idioma){
		return false;
	}
	
	
	/**
	 * Atualiza no banco de dados a lista de idiomas falados pelo aluno.
	 */
	public boolean atualizar(List<Idioma> idiomas) {
		super.iniciaSessaoNeo4J();
		
		return true;
	}
}
