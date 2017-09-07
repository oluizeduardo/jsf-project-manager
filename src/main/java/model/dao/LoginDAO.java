package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import model.pojo.Pessoa;

public class LoginDAO extends DAOBase {

	public LoginDAO() {
	}

	public Pessoa buscaPessoa(String user, String password) {

		super.iniciaSessaoNeo4J();
		
		Pessoa pessoa = null;

		String script = "MATCH (n) WHERE n.email= '" + user + "'and n.senha= '" + password
				+ "' RETURN ID(n) as id, n.email as email, n.senha as senha, n.papel as papel";

		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			pessoa = new Pessoa();
			Record registro = resultado.next();
			pessoa.getContato().setEmail(registro.get("email").asString());
			pessoa.setSenha(registro.get("senha").asString());
			pessoa.setPapel(registro.get("papel").asString());
			pessoa.setId(registro.get("id"));
		}
		
		return pessoa;
	}

}
