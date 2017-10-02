package model.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Aluno;
import model.pojo.Idioma;


public class IdiomaDAO extends DAOBase {


	public boolean salvar(Idioma idioma){
		return false;
	}
	
	
	/**
	 * Atualiza no banco de dados a lista de idiomas falados pelo aluno.
	 */
	public boolean atualizar(Aluno usuario) {
		super.iniciaSessaoNeo4J();
		
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		List<Idioma> idiomas = usuario.getIdiomas();
		
		boolean status = false;
 		transaction = session.beginTransaction();
 		
 		
 		for (Idioma idioma : idiomas) {
				String nomeLingua = idioma.getNomeIdioma();
				String nivelDeConhecimento = idioma.getNivelDeConhecimento();
				
				String script = "MATCH (a:Aluno) WHERE a.email='"+email+"'AND a.senha='"+senha+"' "
		 				+ "CREATE(idi:Idioma{nome:'"+nomeLingua+"'}) "
		 				+ "CREATE(a)-[:CONHECE{nivel:'"+nivelDeConhecimento+"'}]->(idi) "
		 				+ "return idi";
			
				// Executa o script no banco de dados.
	 			transaction.run(script);			
	 			transaction.success();
	 		}
 		
 		try {
			transaction.close();
			session.close();
			status = true;
		} 
		catch (ClientException excep) {
			System.err.println("Erro ao fechar Transaction e Session - IdiomaDAO.atualizar().");
			transaction.failure();
			transaction.close();
			session.close();
			status = false;
		}
				
		return status;
	}


	
	/**
	 * Monta uma lista dos idiomas falados por um aluno.
	 * @param aluno
	 * @return Lista de idiomas.
	 */
	public List<Idioma> listarIdiomasDoAluno(String email, String senha){
		
		List<Idioma> idiomasFalados = new ArrayList<Idioma>();
		
		String script = "MATCH (al:Aluno)-[c:CONHECE]->(idi:Idioma)  "
				+ "WHERE al.email = '"+email+"' AND al.senha = '"+senha+"' "
				+ "RETURN idi.nome as lingua, c.nivel as nivel";
		
		super.iniciaSessaoNeo4J();
		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			
			Record registro = resultado.next();
			String lingua = registro.get("lingua").asString();
			String nivelDeConhecimento = registro.get("nivel").asString();
			
			idiomasFalados.add(new Idioma(lingua, nivelDeConhecimento));		
		}
			
		return idiomasFalados;		
	}
	
	
	
	/**
	 * Exclui no banco de dados a relação que um aluno tem com um determinado idioma.
	 */
	public void excluiIdioma(Aluno aluno, Idioma idioma){
		super.iniciaSessaoNeo4J();
 		transaction = session.beginTransaction();
 		
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		String nomeIdioma = idioma.getNomeIdioma();
		
		String script = "MATCH(a:Aluno)-[c:CONHECE]->(i:Idioma) "
				+ "WHERE a.email='"+email+"' AND a.senha='"+senha+"' "
				+ "AND i.nome = '"+nomeIdioma+"' DELETE c";
	
		// Executa o script no banco de dados.
		transaction.run(script);			
		transaction.success();
 		
 		
 		try {
			transaction.close();
			session.close();
		} 
		catch (ClientException excep) {
			System.err.println("Erro ao fechar Transaction e Session - IdiomaDAO.excluiIdioma().");
			transaction.failure();
		}
	}

}
