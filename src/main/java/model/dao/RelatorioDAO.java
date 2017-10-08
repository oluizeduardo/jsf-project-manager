package model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;


public class RelatorioDAO extends DAOBase {

	
	public RelatorioDAO() { }
	
	
	public List<Map<String, Object>> buscaQtdeDeProjetosPorProfessor(){
		Map<String, Object> map = null;		
		List<Map<String, Object>> lista = new ArrayList<Map<String,Object>>();
		
		
		String script= "MATCH(p:Professor)-[c:COORDENA]->(pro:Projeto) "
				+ "RETURN p.nome as Professor, count((p)-[c]->(pro)) as Qtde";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);		
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			map = new HashMap<String, Object>();
						
			String nomePofessor = registro.get("Professor").asString();
			Integer qtdeProjetos = registro.get("Qtde").asInt();
						
			map.put("Nome", nomePofessor);
			map.put("Qtde", qtdeProjetos);
			
			lista.add(map);
		}
		return lista;
	}
	
}
