package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Professor;
import model.pojo.Projeto;



public class ProjetoDAO extends DAOBase implements AcoesBancoDeDados<Projeto> {

	public ProjetoDAO() { }
	
	
	/**
	 * Salva um novo objeto Projeto no banco de dados.
	 */
	public boolean salvar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
		
		String email = projeto.getCoordenador().getContato().getEmail();
		String senha = projeto.getCoordenador().getSenha();
		String titulo = projeto.getTitulo();
		
		transaction = session.beginTransaction();
		boolean executou = false;//Status do cadastro.
		
		String script = "MATCH (pr:Professor{email:'"+email+"',senha:'"+senha+"'}) "
		+ "CREATE (pj:Projeto {titulo: '" + projeto.getTitulo() 
		+ "', dataInicio:'" + projeto.getDataInicio() 
		+ "', dataPublicacao:'" + projeto.getDataPublicacao()
		+ "', eFinanciado:'" + projeto.getFinanciamento().isExistente()
		+ "', valor:'" + projeto.getFinanciamento().getValor()
		+ "', descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "', categoria:'" + projeto.getCategoria()
		+ "', numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		+ "', resumo:'" + projeto.getResumo()
		+ "', dataFim:'" + projeto.getDataFim()
		+ "'}),(pr)-[:COORDENA{Desde:'"+projeto.getDataInicio()+"'}]->(pj) return pj, pr";
		
		try {
			// Executa o script de criação do projeto.
			transaction.run(script);
			executou = true;
		} catch (Exception e) {
			executou = false;
		}
		
		
		// Se conseguiu executar corretamente o script de criação do projeto.
		if(executou){
			List<Habilidade> habilidadesExigidas = projeto.getHabilidades();
			HabilidadeDAO habilidadeDAO = new HabilidadeDAO();
			
			String scriptHabilidades = "";
			String descricaoHabilidade = "";
			String nivelDeConhecimento = "";
			
			// Percorre a lista de habilidades exigidas pelo projeto.
			for (Habilidade habilidade : habilidadesExigidas) {
				
				descricaoHabilidade = habilidade.getDescricao();
				nivelDeConhecimento = habilidade.getNivel();
				
				// Verifica se já existe tal habilidade no banco de dados.
				if(habilidadeDAO.verificaExistenciaDeHabilidade(descricaoHabilidade)){
					scriptHabilidades ="MATCH (h:Habilidade)"
							+ " WHERE h.nome ='"+descricaoHabilidade+"'"
							+ " MATCH (p:Projeto) WHERE p.titulo='"+titulo+"' "
							+ " CREATE (p)-[:EXIGE{nivel: '"+nivelDeConhecimento+"'}]->(h) return p, h ";
				}else{
					scriptHabilidades ="MATCH (p:Projeto) WHERE p.titulo='"+titulo+"' "
							+ "CREATE (h:Habilidade{nome: '"+descricaoHabilidade+"'}), "
							+ "(p)-[:EXIGE{nivel: '"+nivelDeConhecimento+"'}]->(h) return p, h ";
				}
				
				try{
					// Executa o script no banco de dados.
					transaction.run(scriptHabilidades);
					transaction.success();
				}catch(Exception ex){
					System.err.println("Erro ao executar script no banco de dados - ProjetoDAO.salvar()");
					executou = false;
					continue;
				}
			}
			
			
			//=============== CURSOS ENVOLVIDOS ============================
			
			List<Curso> cursosEnvolvidos = projeto.getCursosEnvolvidos();
			
			for (Curso curso : cursosEnvolvidos) {
				if(existeCurso(curso.getNome())){
					script = "MATCH(c:Curso) WHERE c.nome = '"+curso.getNome()+"' "
						   + "MATCH (p:Projeto) WHERE p.titulo='"+titulo+"' "
						   + "CREATE (p)-[:DESTINADO_A]->(c)";
				}else{
					script = "MATCH (p:Projeto) WHERE p.titulo='"+titulo+"' "
						   + "CREATE(c:Curso{nome:'"+curso.getNome()+"'}),"
						   + "(p)-[:DESTINADO_A]->(c) ";
				}
				
				try{
					// Executa o script no banco de dados.
					transaction.run(script);
					transaction.success();
				}catch(Exception ex){
					System.err.println("Erro ao executar script no banco de dados - ProjetoDAO.salvar()");
					executou = false;
					continue;
				}
			}
			
			// Fecha conexões com o banco de dados.
			try {
				transaction.close();
			} 
			catch (ClientException excep) {
				transaction.failure();
				System.err.println("Erro ao fechar conexão com o banco de dados - ProjetoDAO.salvar()");
			}
			session.close();
		}		
		return executou;
	}

	
	/**
	 * Busca no banco de dados a existência de um determinado curso.
	 * @return verdadeiro ou falso.
	 */
	private boolean existeCurso(String curso){		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run("MATCH (c:Curso) WHERE c.nome = '"+curso+"' return c");
		
		while (resultado.hasNext()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Retorna uma lista com todos os projetos salvos pelos professores.
	 */
	public List<Projeto> listar() {
		
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		String script = "MATCH(pr:Professor)-[:COORDENA]->(pj:Projeto) "
				+ "return id(pj) as ID, "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, "
				+ "pj.dataInicio as Data_Inicio, pj.dataPublicacao as Publicacao, "
				+ "pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, pj.numeroParticipantes as QTD_Participantes, "
				+ "pj.resumo as Resumo, pr.nome as Coordenador";
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
		//	projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asFloat());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
	//		projetoAux.setNumeroDeParticipantes(projetoAtual.get("QTD_Participantes").asInt());
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			
			// Busca a lista de habilidades exigidas por esse projeto.
			projetoAux.setHabilidades(buscaHabilidadesDoProjeto(projetoAux));
			
			projetos.add(projetoAux);
			
		}
		
		return projetos;
	}
	
	
	/**
	 * Retorna uma lista de habilidades exigidas por um determinado projeto.
	 * 
	 * @param projeto
	 * @return Lista de habilidades.
	 */
	private List<Habilidade> buscaHabilidadesDoProjeto(Projeto projeto){
		
		List<Habilidade> habilidadesDoProjeto = new ArrayList<Habilidade>();
		String titulo = projeto.getTitulo();
		String coordenador = projeto.getCoordenador().getNome();
		
		String script = "MATCH (pro:Professor)-[:COORDENA]->(p:Projeto)-[e:EXIGE]->(h:Habilidade) "
				+ "WHERE p.titulo='"+titulo+"' AND pro.nome='"+coordenador+"' "
				+ "return h.nome as Habilidade, e.nivel as Nivel";
	
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record registro = resultado.next();			
			Habilidade habilidade = new Habilidade();
			
			habilidade.setDescricao(registro.get("Habilidade").asString());
			habilidade.setNivel(registro.get("Nivel").asString());
			
			habilidadesDoProjeto.add(habilidade);
		}
			
		return habilidadesDoProjeto;
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
		
		StatementResult resultado = session.run("MATCH(pr:Professor)-[:COORDENA]->(pj:Projeto) "
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
	//		projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asFloat());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
	//		projetoAux.setNumeroDeParticipantes(projetoAtual.get("QTD_Participantes").asInt());
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
		+ "'pj.dataInicio: '" + projeto.getDataInicio()
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
	
	
	
	
	
	/**
	 * Associa um novo nó Habilidade ao nó de um Projeto.
	 */
	public boolean addHabilidade(Projeto projeto, Habilidade habilidade) {			
		
		boolean status = false;		
		String tituloHabilidade = projeto.getTitulo();
		String script;
		String descricaoHabilidade = habilidade.getDescricao();
		String nivelDeConhecimento = habilidade.getNivel();
				
		if(new HabilidadeDAO().verificaExistenciaDeHabilidade(tituloHabilidade)){
			script = "MATCH(h:Habilidade) WHERE h.nome = '"+descricaoHabilidade+"'";
		}else{
			script = "CREATE(h:Habilidade{nome:'"+descricaoHabilidade+"', nivel:'"+nivelDeConhecimento+"'})";
		}

		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();

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
				session.close();
			} 
			catch (ClientException excep) {
				System.err.println("Erro ao fechar Transaction ou Session - ProjetoDAO.addHabilidade()");
			}
		}		
		return status;
	}
}
