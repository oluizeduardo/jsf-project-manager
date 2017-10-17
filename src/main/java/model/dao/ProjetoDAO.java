package model.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;

import model.pojo.Aluno;
import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Notificacao;
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
	 * Busca por projetos no banco de dados.
	 * A busca é feita baseada no script passado no parâmetro do método.
	 * 
	 * @param script O esquema de busca.
	 * @return Uma lista de objetos Projeto.
	 */
	public List<Projeto> buscaProjetos(String script){
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto> projetosLocalizados = new ArrayList<Projeto>();
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setStatus(projetoAtual.get("Status").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
		//	projetoAux.getFinanciamento().setValor(new Float(projetoAtual.get("Valor").asDouble()));
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
		//	projetoAux.setNumeroDeParticipantes(new Integer(projetoAtual.get("QTD_Participantes").asInt()));
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			
			// Define o status atual do projeto.
			getStatusProjeto(projetoAux);
			
			// Busca a lista de habilidades exigidas por esse projeto.
			projetoAux.setHabilidades(buscaHabilidadesDoProjeto(projetoAux));
			projetoAux.setNomeIcone(getNomeIconePorCategoria(projetoAux.getCategoria()));
			
			projetosLocalizados.add(projetoAux);
		}		
		return projetosLocalizados;
	}
	
	
	
	/**
	 * Define o status atual do projeto.
	 * 
	 * @param projeto O projeto analisado.
	 */
	private void getStatusProjeto(Projeto projeto) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");   
	    try {   
	       Date dataInicio = sdf.parse(projeto.getDataInicio());   
	       Date dataFim    = sdf.parse(projeto.getDataFim());   
	       Date dataAtual  = sdf.parse(getDataAtual());
	       		       
	       if(dataAtual.getTime() < dataInicio.getTime()){
	    	   projeto.setStatus("Aguardando Inicio");
	       }else if(dataAtual.getTime() > dataFim.getTime()){
	    	   projeto.setStatus("Finalizado");
	       }else{
	    	   projeto.setStatus("Em Execução");
	       }
	    }catch(ParseException px){
	    	  System.err.println("ERRO NA CONVERSÃO DE DATAS.");
	    }
	}

	

	/**
	 * Retorna a data atual formatada para o padrão de leitura simples.
	 */
	private String getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}
	
	
	
	/**
	 * Retorna o nome do ícone que aparecerá no card do projeto baseado na categoria 
	 * do projeto.
	 * 
	 * @param categoriaProjeto
	 * @return O nome do ícone.
	 */
	private String getNomeIconePorCategoria(String categoriaProjeto){
		if(categoriaProjeto.equals(Projeto.ESTAGIO)){
			return "doc-black";
		}else if(categoriaProjeto.equals(Projeto.EVENTO_EXTERNO)){
			return "doc-blue";
		}else if(categoriaProjeto.equals(Projeto.EVENTO_INTERNO)){
			return "doc-green";
		}else if(categoriaProjeto.equals(Projeto.INICIACAO_CIENTIFICA)){
			return "doc-orange";
		}else if(categoriaProjeto.equals(Projeto.PROJETO_DE_EXTENSAO)){
			return "doc-purple";
		}else if(categoriaProjeto.equals(Projeto.TRABALHO_ACADEMICO)){
			return "doc-red";
		}else{
			return "doc-black";
		}
	}
	
	
	
	/**
	 * Retorna uma lista com todos os projetos salvos pelos professores.
	 */
	public List<Projeto> listar() {
		
		String script = "MATCH(pr:Professor)-[:COORDENA]->(pj:Projeto) "
				+ "return id(pj) as ID, "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, "
				+ "pj.dataInicio as Data_Inicio, pj.dataPublicacao as Publicacao, "
				+ "pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, pj.numeroParticipantes as QTD_Participantes, "
				+ "pj.resumo as Resumo, pr.nome as Coordenador, pj.status as Status";		
		
		return buscaProjetos(script);
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
				
		String email = professor.getContato().getEmail();
		String senha = professor.getSenha();
				
		String script = "MATCH(pr:Professor)-[:COORDENA]->(pj:Projeto) "
				+ "WHERE pr.email = '"+email+"' AND pr.senha = '"+senha+"' RETURN "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, pj.dataInicio as Data_Inicio, "
				+ "pj.dataPublicacao as Publicacao, pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, pj.numeroParticipantes as QTD_Participantes, pj.resumo as Resumo, "
				+ "pr.nome as Coordenador, pj.status as Status";

		return buscaProjetos(script);
	}
	
	
	
	/**
	 * Exlui um determinado projeto no banco de dados.
	 */
	public boolean excluir(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Exclui do banco de dados um projeto do professor.
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
	
	
	
	/**
	 * Retorna uma lista de notificações com base nos alunos que têm interesse
	 * nos projetos de um determinado professor.
	 * 
	 * @param professor O coordenador dos projetos.
	 * @return Uma lista de notificações.
	 */
	public List<Notificacao> getNotificacoesDeInteresseEmProjetos(Professor professor){
		List<Notificacao> lista = new ArrayList<Notificacao>();
		
		String email = professor.getContato().getEmail();
		String senha = professor.getSenha();
		
		String script = "MATCH(c:Curso)<-[:CURSA]-(a:Aluno)-[:TEM_INTERESSE_EM]->"
				+ "(p:Projeto)<-[:COORDENA]-(pr:Professor) "
				+ "WHERE pr.email='"+email+"' AND pr.senha='"+senha+"'"
				+ "RETURN a.nome as NomeAluno, p.titulo as Projeto, "
				+ "c.nome as Curso, ID(p) as ProjetoID, ID(a) as AlunoID";
		
		iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			String nomeAlunoInteressado = registro.get("NomeAluno").asString();
			String projetoDeInteresse = registro.get("Projeto").asString();
			String cursoDoAluno = registro.get("Curso").asString();
			int idAluno = registro.get("AlunoID").asInt();
			int idProjeto = registro.get("ProjetoID").asInt();
			String msg = nomeAlunoInteressado+" do curso de "+cursoDoAluno
					+" se interessa pelo seu projeto \""+projetoDeInteresse+"\"";
			
			lista.add(new Notificacao(msg, idProjeto, idAluno));
		}
		return lista;
	}
	
	
	
	/**
	 * Retorna uma lista de notificações com base nos alunos que tiveram 
	 * a solicitação de participação em um projeto aprovada pelo professor.
	 * 
	 * @param aluno O aluno do qual se deseja buscar as notificações.
	 * @return Uma lista de notificações.
	 */
	public List<Notificacao> getNotificacoesDeParticipacaoEmProjetos(Aluno aluno) {

		List<Notificacao> lista = new ArrayList<Notificacao>();
		
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		
		String script = "MATCH(p:Professor)-[:COORDENA]->(pr:Projeto)<-"
				+ "[par:PARTICIPA{msglida: false}]-"
				+ "(eu:Aluno{email:'"+email+"', senha:'"+senha+"'}) "
				+ "RETURN p.nome as Coordenador, pr.titulo as Projeto, p.sexo as Sexo";
		
		iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			Record registro = resultado.next();
			String coordenador = registro.get("Coordenador").asString();
			String projeto = registro.get("Projeto").asString();
			String sexo = registro.get("Sexo").asString();
			String sexomsg = sexo.equals("Feminino") ? "A professora" : "O professor";
			
			String msg = sexomsg+" "+coordenador+" aprovou sua participação no projeto "+projeto+".";
			
			lista.add(new Notificacao(msg));
		}
		return lista;
	}
	
	
	
	
	/**
	 * Atualiza no banco de dados os projetos que o aluno faz parte e que já tiveram 
	 * a notificação de aprovação de participação lida.
	 * 
	 * @param aluno
	 */
	public void atualizaMensagemDeParticipacaoLida(Aluno aluno){
		
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		
		String script="MATCH k=(eu:Aluno{email:'"+email+"', senha:'"+senha+"'})-"
				+ "[par:PARTICIPA]->(p:Projeto) SET par.msglida = NULL return k";
		
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();

		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			
		}catch(Exception ex){
			System.err.println("Erro ao executar script! - ProjetoDAO.atualizaMensagemDeParticipacaoLida()");
		}finally {
			try {
				transaction.close();
				session.close();
			} 
			catch (ClientException excep) {
				System.err.println("Erro ao fechar Transaction ou Session - ProjetoDAO.atualizaMensagemDeParticipacaoLida()");
			}
		}
	}
	
	
	
	
	/**
	 * Monta no banco de dados uma relação de "PARTICIPA" entre aluno e projeto.
	 * @param alunoID
	 * @param projetoID
	 */
	public void inscreveAlunoEmProjeto(int alunoID, int projetoID){
		
		String script ="MATCH(a:Aluno)-[i:TEM_INTERESSE_EM]->(p:Projeto) "
				+ "WHERE id(a)="+alunoID+" AND ID(p)="+projetoID+"  DELETE i "
				+ "CREATE(a)-[r:PARTICIPA{msglida:false}]->(p) RETURN a, r, p";
		
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();

		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			
		}catch(Exception ex){
			System.err.println("Erro ao executar script - ProjetoDAO.inscreveAlunoEmProjeto()");
			System.err.println(ex.getMessage());
		}finally {
			try {
				transaction.close();
				session.close();
			} 
			catch (ClientException excep) {
				System.err.println("Erro ao fechar Transaction ou Session - ProjetoDAO.inscreveAlunoEmProjeto()");
				System.err.println(excep.getMessage());
			}
		}		
	}
	
	
	
	/**
	 * Recusa o interesse do aluno em um projeto do professor.
	 * Apaga a relação de "TEM_INTERESSE_EM".
	 * 
	 * @param alunoID
	 * @param projetoID
	 */
	public void recusaAlunoEmProjeto(int alunoID, int projetoID){
		
		String script ="MATCH(a:Aluno)-[i:TEM_INTERESSE_EM]->(p:Projeto) "
				+ "WHERE id(a)="+alunoID+" AND ID(p)="+projetoID+"  DELETE i RETURN a, p";
		
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();

		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			
		}catch(Exception ex){
			System.err.println("Erro ao executar script - ProjetoDAO.recusaAlunoEmProjeto()");
		}finally {
			try {
				transaction.close();
				session.close();
			} 
			catch (ClientException excep) {
				System.err.println("Erro ao fechar Transaction ou Session - ProjetoDAO.recusaAlunoEmProjeto()");
			}
		}		
	}


	
	
	
}
