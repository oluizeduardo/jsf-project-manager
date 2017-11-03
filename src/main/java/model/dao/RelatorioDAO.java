package model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;


public class RelatorioDAO extends DAOBase {

	
	public RelatorioDAO() { }
	
	
	/**
	 * Retorna uma lista contendo objetos Map. 
	 * Cada Map é um objeto contendo uma chave e um valor associado.
	 * Para a chave "habilidade", tem-se o nome de uma habilidade.
	 * Para cada chave "Qtde", tem-se o número de vezes que tal habilidade
	 * foi encontrada entre os alunos de um determinado curso.
	 * 
	 * @param nomeCurso O nome do curso onde se deseja buscar as habilidades mais comuns.
	 */
	public List<Map<String, Object>> buscaHabilidadesMaisComuns(String nomeCurso){
		Map<String, Object> map = null;		
		List<Map<String, Object>> lista = new ArrayList<Map<String,Object>>();		
		
		String script = "MATCH(h:Habilidade)<-[c:CONHECE]-(a:Aluno)-[:CURSA]->(cs:Curso{nome:'"+nomeCurso+"'}) "
				+ "RETURN h.nome as Habilidade, count((a)<-[c]-(a)) as Qtde";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			map = new TreeMap<String, Object>();
			
			String habilidade = registro.get("Habilidade").asString();
			Integer qtde = registro.get("Qtde").asInt();
						
			map.put("Habilidade", habilidade);
			map.put("Qtde", qtde);
			
			lista.add(map);
		}
		session.close();
		return lista;
	}
	
	
	
	
	/**
	 * Retorna uma lista contendo objetos Map. 
	 * Cada Map é um objeto contendo uma chave e um valor associado.
	 * Para a chave "Professor", tem-se o nome de um professor.
	 * Para cada chave "Qtde", tem-se o número de projetos cadastrados 
	 * por esse professor.
	 */
	public List<Map<String, Object>> buscaQtdeDeProjetosPorProfessor(){
		Map<String, Object> map = null;		
		List<Map<String, Object>> lista = new ArrayList<Map<String,Object>>();
		
		
		String script= "MATCH(p:Professor)-[c:COORDENA]->(pro:Projeto) "
				+ "RETURN p.nome as Professor, count((p)-[c]->(pro)) as Qtde";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);		
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			map = new TreeMap<String, Object>();
			
			String nomePofessor = registro.get("Professor").asString();
			Integer qtdeProjetos = registro.get("Qtde").asInt();
						
			map.put("Nome", nomePofessor);
			map.put("Qtde", qtdeProjetos);
			
			lista.add(map);
		}
		session.close();
		return lista;
	}
<<<<<<< HEAD

	
	/**
	 * Retorna uma lista contendo as informações do número de projetos
	 * publicados durante o ano destinados a um determinado curso.
	 * 
	 * @param curso O curso que se deseja saber.
	 * @return Uma lista de ProjetosPublicadosPorCurso.
	 */
	public ProjetosPublicadosPorCurso[] getProjetosPublicadosDuranteAno(String curso) {
		
		ProjetosPublicadosPorCurso[] lista = iniciaVetor(12);
				
		String script = "MATCH(pro:Projeto)-[:DESTINADO_A]->(c:Curso{nome:'"+curso+"'}) "
				+ "RETURN toInteger(substring(pro.dataPublicacao,3, 2)) "
				+ "as Mes, count(pro) as QtdeProjetos ORDER BY Mes ASC";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);		
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			ProjetosPublicadosPorCurso pj = new ProjetosPublicadosPorCurso();
						
			int mes = registro.get("Mes").asInt();
			pj.setCurso(curso);
			pj.setMes(mes);
			pj.setQuantidade(registro.get("QtdeProjetos").asInt());
			
			lista[mes-1] = pj;//Para começar na posição 0 do vetor.
		}
		session.close();
		return lista;
	}
=======
>>>>>>> parent of affd899... ImplementaÃ§Ã£o no grÃ¡fico de linhas na pÃ¡gina de relatÃ³rios.
	
	
	
	/**
	 * Inicia um vetor.
	 * @param tamanho o tamanho do vetor que se deseja criar.
	 * @return Um vetor preenchido com objetos instanciados.
	 */
	private ProjetosPublicadosPorCurso[] iniciaVetor(int tamanho){
		ProjetosPublicadosPorCurso[] lista = new ProjetosPublicadosPorCurso[tamanho];
		
		for(int i=0; i < tamanho; i++){
			lista[ i ] = new ProjetosPublicadosPorCurso();
		}		
		return lista;
	}
	
}
