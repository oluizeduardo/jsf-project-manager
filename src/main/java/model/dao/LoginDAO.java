package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.ClientException;

import model.dao.databaseconfig.ConnectionNeo4j;
import model.pojo.Usuario;

public class LoginDAO {
	
private Session session = null;
	
	public LoginDAO() {
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
	public Usuario verificarLogin(String user, String password) {
		
		Usuario usuarioNovo = new Usuario();
		
		System.out.println("VARIAVEIS QUE VIERAM DO PARAMETRO");
		System.out.println(user);
		System.out.println(password);
		
		//boolean status = true;
		
		System.out.println("buscando!!");
				
		StatementResult resultado = session.run("MATCH (n) WHERE n.email= '" 
		+ user + "'and n.senha= '" + password + "' RETURN n.email as email, n.senha as senha");
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			String email = registro.get("email").asString();
			String senha = registro.get("senha").asString();
			
			Usuario usuarioAux = new Usuario();
			usuarioAux.getContato().setEmail(email);
			usuarioAux.setSenha(senha);
			
			System.out.println("VARIAVEIS DEPOIS DA BUSCA");
			System.out.println(usuarioAux.getSenha());
			System.out.println(usuarioAux.getContato().getEmail());
			
			usuarioNovo = usuarioAux;
		}
		
		session.close();
		return usuarioNovo;
	}
		
}


