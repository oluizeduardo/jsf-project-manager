package model.dao;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import model.dao.databaseconfig.ConnectionNeo4j;

/**
 * 
 */
public class DAOBase {

	
	/**Guarda uma nova sessão com o banco de dados.*/
	protected Session session = null;
	
	/**Necessário para fazer transações no banco de dados.*/
	protected Transaction transaction = null;
	
	
	
	public DAOBase() { }
	
	
	public void iniciaSessaoNeo4J(){
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
}
