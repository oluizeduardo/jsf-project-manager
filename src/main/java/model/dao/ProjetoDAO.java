package model.dao;

import java.util.List;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;

import model.dao.databaseconfig.ConnectionNeo4j;
import model.pojo.Projeto;

public class ProjetoDAO implements AcoesBancoDeDados<Projeto> {

	/**Guarda uma nova sessão com o banco de dados.*/
	protected Session session = null;
	
	/**Necessário para fazer transações no banco de dados.*/
	protected Transaction transaction = null;
	
	
	
	public ProjetoDAO() {
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
	
	public boolean salvar(Projeto obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Projeto> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public void excluir(Projeto obj) {
		// TODO Auto-generated method stub
		
	}

	public void atualizar(Projeto obj) {
		// TODO Auto-generated method stub
		
	}

	

}
