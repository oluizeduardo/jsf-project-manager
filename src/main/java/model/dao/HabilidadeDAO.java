package model.dao;

import java.util.List;
import model.pojo.Habilidade;



public class HabilidadeDAO extends DAOBase {


	public boolean salvar(Habilidade hab){
		return false;
	}
	
	
	/**
	 * Atualiza no banco de dados a lista de habilidades do aluno.
	 */
	public boolean atualizar(List<Habilidade> habilidades) {
		super.iniciaSessaoNeo4J();
		
		return true;
	}
}
