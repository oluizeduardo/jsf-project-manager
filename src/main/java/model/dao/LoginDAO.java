package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;



public class LoginDAO extends DAOBase {
	

	public LoginDAO() { }
	
	
	public boolean validaLogin(String user, String password) {

		super.iniciaSessaoNeo4J();
		
		String script = "MATCH (n) WHERE n.email= '" + user + "'and n.senha= '" + password
						+ "' RETURN n.email as email, n.senha as senha";
		
		StatementResult resultado = session.run(script);

		String emailAux = "";
		String senhaAux = "";

		while (resultado.hasNext()) {
			Record registro = resultado.next();
			emailAux = registro.get("email").asString();
			senhaAux = registro.get("senha").asString();
			
			if (emailAux.equals(user) && senhaAux.equals(password)) {
				return true;
			}
		}
		session.close();
		
		return false;
	}
}
