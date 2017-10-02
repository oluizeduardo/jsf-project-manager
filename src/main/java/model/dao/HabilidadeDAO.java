package model.dao;

import java.util.List;

import org.neo4j.driver.v1.exceptions.ClientException;

import model.pojo.Aluno;
import model.pojo.Habilidade;



public class HabilidadeDAO extends DAOBase {


	public boolean salvar(Habilidade hab){
		return false;
	}
	
	
	/**
	 * Atualiza no banco de dados a lista de habilidades do aluno.
	 */
	public boolean atualizar(Aluno usuario) {
		super.iniciaSessaoNeo4J();
				
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		List<Habilidade> habilidades = usuario.getHabilidades();
		
		boolean status = false;
 		transaction = session.beginTransaction();
 		
 		
 		for (Habilidade hab : habilidades) {
			String habilidade = hab.getDescricao();
			String nivelDeConhecimento = hab.getNivel();
			
			String script = "MATCH (a:Aluno) WHERE a.email='"+email+"'AND a.senha='"+senha+"' "
	 				+ "CREATE(h:Habilidade{nome:'"+habilidade+"'}) "
	 				+ "CREATE(a)-[:CONHECE{nivel:'"+nivelDeConhecimento+"'}]->(h) "
	 				+ "return h";
		
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
			System.err.println("Erro ao fechar Transaction e Session - HabilidadeDAO.atualizar().");
			transaction.failure();
			transaction.close();
			session.close();
			status = false;
		}
				
		return status;
	}
}
