package model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import model.pojo.ProjetosPublicadosPorCurso;


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
		
		String script = "MATCH(h:Habilidade)<-[c:CONHECE]-(a:Aluno)-[:CURSA]->"
				+ "(cs:Curso{nome:'"+nomeCurso+"'}) "
				+ "RETURN h.nome as Habilidade, "
				+ "count((a)-[c]->(h)) as Qtde ORDER BY Qtde DESC";
		
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
	 * Busca a quantidade de projetos publicados por cada professor cadastrado
	 * no sistema.
	 * 
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
				+ "RETURN p.nome as Professor, count((p)-[c]->(pro)) "
				+ "as Qtde ORDER BY Qtde ASC";
		
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

	
	/**
	 * Retorna uma lista contendo as informações do número de projetos
	 * publicados durante o ano destinados a um determinado curso.
	 * 
	 * @param curso O curso que se deseja saber.
	 * @return Uma lista de ProjetosPublicadosPorCurso.
	 */
	public List<ProjetosPublicadosPorCurso> getProjetosPublicadosDuranteAno(String curso) {
		
		List<ProjetosPublicadosPorCurso> lista = new ArrayList<ProjetosPublicadosPorCurso>();
				
		String script = "MATCH(pro:Projeto)-[:DESTINADO_A]->(c:Curso{nome:'"+curso+"'}) "
				+ "RETURN toInteger(substring(pro.dataPublicacao,3, 2)) "
				+ "as Mes, count(pro) as QtdeProjetos ORDER BY Mes ASC";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);		
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			ProjetosPublicadosPorCurso pj = new ProjetosPublicadosPorCurso();
			
			pj.setCurso(curso);
			pj.setMes(registro.get("Mes").asInt());
			pj.setQuantidade(registro.get("QtdeProjetos").asInt());
			
			lista.add(pj);
		}
		session.close();
		return lista;
	}
	
}
