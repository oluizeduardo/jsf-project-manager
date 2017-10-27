package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Aluno;
import model.pojo.Habilidade;



public class HabilidadeDAO extends DAOBase {


	
	
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
		String conhecimento = novaHabilidade.getNivel();

		boolean existehabilidade = verificaExistenciaDeHabilidade(habilidade);
		String scriptHabilidade;
		
		if(existehabilidade){
			scriptHabilidade = "MATCH(h:Habilidade) WHERE h.nome = '"+habilidade+"'";
		}else{
			scriptHabilidade = "CREATE(h:Habilidade{nome:'"+habilidade+"'}) ";
		}
		
		
		int nivel = converteNivelDeConhecimento(conhecimento);
		
		String script = "MATCH (a:Aluno) WHERE a.email='"+email+"'AND a.senha='"+senha+"' "
 	 				+ scriptHabilidade
 	 				+ "CREATE(a)-[:CONHECE{descricao:'"+conhecimento+"', nivel: "+nivel+"}]->(h) "
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
	 * Converte o nível de conhecimento. Para cada nível descrito em uma String
	 * existe um nível dado em int.
	 * 
	 * Esse valor do nível de conhecimento dado em int é fundamental para as
	 * buscas no banco de dados baseado em nível de conhecimento em uma
	 * determinada habilidade.
	 * 
	 * Básico=1, Médio=2, Avançado=3.
	 * 
	 * @param nivelStr A descrição do bível em String.
	 * @return Um int indicando o níel da habilidade.
	 */
	private int converteNivelDeConhecimento(String nivelStr){
		if(nivelStr.equals("Avançado")){
			return 3;
		}else if(nivelStr.equals("Médio")){
			return 2;
		}else{
			return 1;
		}
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
	 * Monta uma lista com todas as habilidades cadastradas no banco de dados.
	 * Busca as habilidades associadas aos projetos e aos alunos.
	 */
	public List<Habilidade> listaHabilidades(){
		
		List<Habilidade> habilidades = new ArrayList<Habilidade>();
		
		String script = "MATCH (h:Habilidade) RETURN DISTINCT h.nome AS Habilidade";
		
		super.iniciaSessaoNeo4J();		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			
			Record registro = resultado.next();
			String strhabilidade = registro.get("Habilidade").asString();
			
			Habilidade hab = new Habilidade();
			hab.setDescricao(strhabilidade);			
			habilidades.add(hab);
		}			
		return habilidades;		
	}
	
	
	
	
	/**
	 * Monta uma lista das habilidades de um aluno.
	 */
	public List<Habilidade> listarHabilidadesDoAluno(String email, String senha){
		
		List<Habilidade> habilidades = new ArrayList<Habilidade>();
		
		String script = "MATCH (al:Aluno)-[c:CONHECE]->(h:Habilidade)  "
				+ "WHERE al.email = '"+email+"' AND al.senha = '"+senha+"' "
				+ "RETURN h.nome as habilidade, c.descricao as DescNivel";
		
		super.iniciaSessaoNeo4J();		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			
			Record registro = resultado.next();
			String habilidade = registro.get("habilidade").asString();
			String nivelDeConhecimento = registro.get("DescNivel").asString();
			
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
