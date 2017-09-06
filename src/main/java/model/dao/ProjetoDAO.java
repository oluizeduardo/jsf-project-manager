package model.dao;

import java.util.List;
import model.pojo.Projeto;



public class ProjetoDAO extends DAOBase implements AcoesBancoDeDados<Projeto> {

	
	public ProjetoDAO() { }
	
	
	public boolean salvar(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		return false;
	}

	public List<Projeto> listar() {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		return null;
	}

	public void excluir(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		
	}

	public void atualizar(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		
	}

	

}
