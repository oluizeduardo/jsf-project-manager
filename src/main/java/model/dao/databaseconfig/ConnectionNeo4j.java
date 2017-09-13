package model.dao.databaseconfig;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class ConnectionNeo4j {

	Driver driver = null;
	Session session = null;

	public ConnectionNeo4j() {
		criarDriver();
		criarSession();
		System.out.println("Iniciada conexao com o banco de dados.");
	}
	
	public void criarDriver() {		
		try {
			this.driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "123456"));
		} catch (Exception e) {
			System.err.println("Erro ao criar conexão com o banco de dados.");
		}
	}

	public void criarSession() {
		try {
			this.session = driver.session();
		} catch (Exception e) {
			System.err.println("Erro ao criar Session.");
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return session;
	}
}
