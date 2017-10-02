package model.dao;

import java.util.List;

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
}
