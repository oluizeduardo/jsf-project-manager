package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import model.dao.databaseconfig.ConnectionNeo4j;

public class LoginDAO {

	private Session session = null;

	public LoginDAO() {
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}

	public boolean verificarLogin(String user, String password) {

		System.out.println("VARIAVEIS QUE VIERAM DO PARAMETRO");
		System.out.println(user);
		System.out.println(password);
		System.out.println("realizando busca!!");

		StatementResult resultado = session.run("MATCH (n) WHERE n.email= '" + user + "'and n.senha= '" + password
				+ "' RETURN n.email as email, n.senha as senha");

		String emailAux = "";
		String senhaAux = "";

		while (resultado.hasNext()) {
			Record registro = resultado.next();
			emailAux = registro.get("email").asString();
			senhaAux = registro.get("senha").asString();
		}

		session.close();

		if (emailAux.equals(user) && senhaAux.equals(password)) {
			return true;
		}

		else {
			return false;
		}
	}
}
