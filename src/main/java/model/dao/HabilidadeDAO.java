package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
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
	public boolean atualizar(Aluno usuario, Habilidade novaHabilidade) {
				
		String email = usuario.getContato().getEmail();
		String senha = usuario.getSenha();
		
		boolean status = false;
		super.iniciaSessaoNeo4J();
 		transaction = session.beginTransaction();
 		
 		
		String habilidade = novaHabilidade.getDescricao();
		String nivelDeConhecimento = novaHabilidade.getNivel();

		boolean existehabilidade = verificaExistenciaDeHabilidade(habilidade);
		String scriptHabilidade;
		
		if(existehabilidade){
			scriptHabilidade = "MATCH(h:Habilidade) WHERE h.nome = '"+habilidade+"'";
		}else{
			scriptHabilidade = "CREATE(h:Habilidade{nome:'"+habilidade+"'}) ";
		}
		
		
		String script = "MATCH (a:Aluno) WHERE a.email='"+email+"'AND a.senha='"+senha+"' "
 	 				+ scriptHabilidade
 	 				+ "CREATE(a)-[:CONHECE{nivel:'"+nivelDeConhecimento+"'}]->(h) "
 	 				+ "return a, h";
	
		// Executa o script no banco de dados.
		transaction.run(script);			
		transaction.success();
 		
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
	
	
	
	/**
	 * Verifica se já existe no banco de dados uma habilidade com tal descrição.
	 * 
	 * @param nome O nome da habilidade
	 * @return verdadeiro ou false.
	 */
	public boolean verificaExistenciaDeHabilidade(String nome){
		
		String script = "MATCH (h:Habilidade) WHERE h.nome = '"+nome+"' return h";
		
		super.iniciaSessaoNeo4J();		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * Monta uma lista das habilidades de um aluno.
	 */
	public List<Habilidade> listarHabilidadesDoAluno(String email, String senha){
		
		List<Habilidade> habilidades = new ArrayList<Habilidade>();
		
		String script = "MATCH (al:Aluno)-[c:CONHECE]->(h:Habilidade)  "
				+ "WHERE al.email = '"+email+"' AND al.senha = '"+senha+"' "
				+ "RETURN h.nome as habilidade, c.nivel as nivel";
		
		super.iniciaSessaoNeo4J();		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			
			Record registro = resultado.next();
			String habilidade = registro.get("habilidade").asString();
			String nivelDeConhecimento = registro.get("nivel").asString();
			
			habilidades.add(new Habilidade(habilidade, nivelDeConhecimento));
		}			
		return habilidades;		
	}
	
	
	
	/**
	 * Exclui no banco de dados a relação que um aluno tem com uma habilidade.
	 */
	public void excluiHabilidade(Aluno aluno, Habilidade habilidade){
		super.iniciaSessaoNeo4J();
 		transaction = session.beginTransaction();
 		
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		String descricao = habilidade.getDescricao();
		
		String script = "MATCH(a:Aluno)-[c:CONHECE]->(h:Habilidade) "
				+ "WHERE a.email='"+email+"' AND a.senha='"+senha+"' "
				+ "AND h.nome = '"+descricao+"' DELETE c";
	
		// Executa o script no banco de dados.
		transaction.run(script);			
		transaction.success();
 		
 		
 		try {
			transaction.close();
			session.close();
		} 
		catch (ClientException excep) {
			System.err.println("Erro ao fechar Transaction e Session - HabilidadeDAO.excluiHabilidade().");
			transaction.failure();
		}
	}
	
	
	
}
