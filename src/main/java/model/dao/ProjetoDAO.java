package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Pessoa;
import model.pojo.Professor;
import model.pojo.Projeto;
import web.SessionUtil;

public class ProjetoDAO extends DAOBase implements AcoesBancoDeDados<Projeto> {

	public ProjetoDAO() { }
	
	
	/**
	 * Salva um novo objeto Projeto no banco de dados.
	 */
	public boolean salvar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
		
		Pessoa usuarioLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = usuarioLogado.getContato().getEmail();
		String senha = usuarioLogado.getSenha();
		String titulo = projeto.getTitulo();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "MATCH (pr:Professor{email:'"+email+"',senha:'"+senha+"'}) CREATE (pj:Projeto {titulo: '" + projeto.getTitulo() 
		+ "', dataInicio:'" + projeto.getDataInicio() 
		+ "', dataPublicacao:'" + projeto.getDataPublicacao()
		+ "', eFinanciado:'" + projeto.getFinanciamento().isExistente()
		+ "', valor:'" + projeto.getFinanciamento().getValor()
		+ "', descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "', categoria:'" + projeto.getCategoria()
		+ "', numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		+ "', resumo:'" + projeto.getResumo()
		+ "', dataFim:'" + projeto.getDataFim()
		+ "'}),(pr)-[:COOORDENA{Desde:'"+projeto.getDataInicio()+"'}]->(pj)";
		
		String script2="match (pjh:ProjetoHabilidades)where pjh.projetoPai='"+titulo+"' match (pj:Projeto)where pj.titulo='"+titulo+"' create (pj)-[:EXIGE]->(pjh) return pjh,pj";
				
		try{
			// Executa o script no banco de dados.
			transaction.run(script);
			transaction.run(script2);
			transaction.success();
			
			status = true;
		}catch(Exception ex){
			status = false;
			
		}finally {
			try {
				transaction.close();
			} 
			catch (ClientException excep) {
				transaction.failure();
				transaction.close();
			}
		}
		session.close();
		
		return status;
	}

	
	/**
	 * Retorna uma lista com todos os projetos salvos pelos professores.
	 */
	public List<Projeto> listar() {
		
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		
		StatementResult resultado = session.run("MATCH(pr:Professor)-[:COOORDENA]->(pj:Projeto) return id(pj) as ID, "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, pj.dataInicio as Data_Inicio, "
				+ "pj.dataPublicacao as Publicacao, pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, pj.numeroParticipantes as QTD_Participantes, pj.resumo as Resumo, "
				+ "pr.nome as Coordenador");
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
		//	projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asDouble());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
		//	projetoAux.setNumeroDeParticipantes(projetoAtual.get("QTD_Participantes").asInt());
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			
			projetos.add(projetoAux);
			
		}
		
		return projetos;
	}
	
	/**
	 * Retorna uma lista com todos os projetos cadastrados
	 * por um professor específico.
	 */
	public List<Projeto> listarPorProfessor(Professor professor) {
		
		super.iniciaSessaoNeo4J();
		
		String email = professor.getContato().getEmail();
		String senha = professor.getSenha();
		
		
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		
		StatementResult resultado = session.run("MATCH(pr:Professor)-[:COOORDENA]->(pj:Projeto) "
				+ "WHERE pr.email = '"+email+"' AND pr.senha = '"+senha+"' return id(pj) as ID, "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, pj.dataInicio as Data_Inicio, "
				+ "pj.dataPublicacao as Publicacao, pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, pj.numeroParticipantes as QTD_Participantes, pj.resumo as Resumo, "
				+ "pr.nome as Coordenador");
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
		//	projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asDouble());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
		//	projetoAux.setNumeroDeParticipantes(projetoAtual.get("QTD_Participantes").asInt());
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			
			projetos.add(projetoAux);
			
		}
		
		return projetos;
	}
	
	/**
	 * Exlui um determinado projeto no banco de dados.
	 */
	public boolean excluir(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		// Primeiro deve excluir o relacionamento: MATCH p=()-[r:COOORDENA]->() DELETE r
		return false;
	}

	
	/**
	 * Atualiza os dados de um objeto projeto no banco de dados.
	 */
	public boolean atualizar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;
		
		String script = "MATCH (pj:Projeto) WHERE pj.titulo = '" + projeto.getTitulo()
		+ "' SET pj.titulo:'" + projeto.getTitulo()
		+ "'pj.dataFim: '" + projeto.getDataFim()
		+ "pj.dataInicio: '" + projeto.getDataInicio()
		+ "'pj.dataPublicacao:'" + projeto.getDataPublicacao()
		+ "'pj.eFinanciado:'" + projeto.getFinanciamento().isExistente()
		+ "'pj.valor:'" + projeto.getFinanciamento().getValor()
		+ "'pj.descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "'pj.categoria:'" + projeto.getCategoria()
		+ "'pj.numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		+ "'pj.resumo:'" + projeto.getResumo()
		+"'RETURN pj";
		
		
		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			status = true;
		}catch(Exception ex){
			status = false;
			
		}finally {
			try {
				transaction.close();
			} 
			catch (ClientException excep) {
				transaction.failure();
				transaction.close();
			}
		}
		session.close();
		
		return status;	
	}
	
	public boolean addHabilidade(Projeto projeto,String nome, String nivel) {
		
		String titulo = projeto.getTitulo();
		
		System.out.println("Projeto pai" + titulo);
		
		System.out.println("Salvando a habilidade do projeto "+nome+ "///"+nivel+" ");
		
		super.iniciaSessaoNeo4J();
		boolean status = false;
		transaction = session.beginTransaction();
		
		String script = "CREATE(pjh:ProjetoHabilidades{nomeHabilidade:'"+nome+"',nivel:'"+nivel+"',projetoPai:'"+titulo+"'}) return pjh";

		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			status = true;
			
		}catch(Exception ex){
			status = false;
		}finally {
			try {
				transaction.close();
			} 
			catch (ClientException excep) {
				transaction.failure();
				transaction.close();
			}
		}
		session.close();
		return status;
	}
}
