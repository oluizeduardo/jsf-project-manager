package model.dao;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import model.dao.databaseconfig.ConnectionNeo4j;

/**
 * 
 */
public class DAOBase {

	
	/**Guarda uma nova sess�o com o banco de dados.*/
	protected Session session = null;
	
	/**Necess�rio para fazer transa��es no banco de dados.*/
	protected Transaction transaction = null;
	
	
	
	public DAOBase() { }
	
	
	public void iniciaSessaoNeo4J(){
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
}
