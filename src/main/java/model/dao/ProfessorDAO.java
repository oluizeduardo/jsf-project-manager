package model.dao;

import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Pessoa;
import model.pojo.Professor;
import web.SessionUtil;


public class ProfessorDAO extends DAOBase implements AcoesBancoDeDados<Professor> {
	
	
	public ProfessorDAO() { }
	
	
	/**
	 * Atualiza no banco de dados o registro de um Professor específico.
	 */
	public boolean atualizar(Professor professor) {
		super.iniciaSessaoNeo4J();
		boolean status = false;//Status da atualização.
		
		// Retorna o objeto com os valroes do usuário logado no sistema.
		Pessoa usuarioLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = usuarioLogado.getContato().getEmail();
		String senha = usuarioLogado.getSenha();
		// Scripts.
		String script="", script2="";
		
		transaction = session.beginTransaction();
		
		script = "MATCH (n:Professor) WHERE n.email = '" +email+ "'AND n.senha ='" +senha+ "' "
				+ "SET n+= {nome: '" + professor.getNome()
				+ "', documentoCPF:'" + professor.getDocumentoCPF()
				+ "', documentoRG:'" + professor.getDocumentoRG()
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', matricula:'" + professor.getMatricula()
				+ "', senha:'" + professor.getSenha()
				+ "', sexo:'" + professor.getSexo() 
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', titulacao:'" + professor.getTitulacao()
				+ "', dataNascimento:'" + professor.getDataNascimento() 
				+ "', dataAdmissao:'" + professor.getDataAdmissao()
				+ "', email:'" + professor.getContato().getEmail() 
				+ "', site:'" + professor.getContato().getSite() 
				+ "', skype:'" + professor.getContato().getSkype()
				+ "', telefone:'" + professor.getContato().getTelefone()
				+ "', bairro:'" + professor.getEndereco().getBairro()
				+ "', cidade:'" + professor.getEndereco().getCidade() 
				+ "', estado:'" + professor.getEndereco().getEstado()
				+ "', rua:'" + professor.getEndereco().getRua() + "'} RETURN n";
		
		
		String curso = professor.getCurso().getNome();
		
		// Se existe o curso, busca o nó para gerar associação.
		if(existeCurso(curso)){
			script2 = "MATCH (p:Professor) WHERE p.nome='"+professor.getNome()+"' "
					+ "AND p.email='"+professor.getContato().getEmail()+"' "
					+ "AND p.senha='"+professor.getSenha()+"' "
					+ "MATCH (c:Curso) WHERE c.nome = '"+curso+"' "
					+ "CREATE (p)-[:LECIONA]->(c) return p, c";
		
		// Se não existir, um novo nó deve ser criado e associado ao aluno.
		}else{
			script2 = "MATCH (p:Professor) WHERE p.nome='"+professor.getNome()+"' "
					+ "AND p.email='"+professor.getContato().getEmail()+"' "
					+ "AND p.senha='"+professor.getSenha()+"' "
					+ "CREATE (c:Curso{nome:'"+curso+"'}) "
					+ "CREATE (p)-[:LECIONA]->(c) return p, c";
		}
		
		// Remove o aluno de qualquer curso que ele já esteja.
		// O aluno não pode estar crsando mais de um curso ao mesmo tempo.
		removeProfessorDoCurso(professor);
		
		
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
	 * Remove o professor de qualquer curso no qual ele esteja lecionando.
	 * 
	 * @param professor
	 * @return Status da exclusão.
	 */
	private boolean removeProfessorDoCurso(Professor professor){
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();
		boolean status = false;
		
		String script = "MATCH (a:Professor)-[le:LECIONA]->(:Curso) DELETE le";
		
		try{
			transaction.run(script);			
			transaction.success();			
			status = true;
			
		}catch(Exception ex){
			ex.printStackTrace();
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
		return status;
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
	 * Salva no banco de dados o registro de um Professor.
	 * 
	 * Retorna o status do cadastro no banco.
	 */
	public boolean salvar(Professor professor) {
		
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script="";
		String scriptCriaProfessor = "CREATE (pr:Professor {nome: '" + professor.getNome()
				+ "', papel:'" + professor.getPapel()
				+ "', documentoCPF:'" + professor.getDocumentoCPF() 
				+ "', documentoRG:'" + professor.getDocumentoRG()
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', matricula:'" + professor.getMatricula()
				+ "', dataAdmissao:'" + professor.getDataAdmissao()
				+ "', senha:'" + professor.getSenha()
				+ "', sexo:'" + professor.getSexo()
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', titulacao:'" + professor.getTitulacao()
				+ "', email:'" + professor.getContato().getEmail()
				+ "', site:'" + professor.getContato().getSite()
				+ "', skype:'" + professor.getContato().getSkype()
				+ "', telefone:'" + professor.getContato().getTelefone()
				+ "', dataNascimento:'" + professor.getDataNascimento()
				+ "', bairro:'" + professor.getEndereco().getBairro()
				+ "', cidade:'" + professor.getEndereco().getCidade()
				+ "', estado:'" + professor.getEndereco().getEstado()
				+ "', rua:'" + professor.getEndereco().getRua() + "'})";
		
		String curso = professor.getCurso().getNome();
		if(existeCurso(curso)){
			String scriptBuscaCurso = " MATCH(c:Curso) WHERE c.nome = '"+curso+"' ";
			String scriptCriaRelacao = "CREATE(pr)-[:LECIONA]->(c) RETURN pr, c";
			script = scriptBuscaCurso + scriptCriaProfessor + scriptCriaRelacao;
		}else{
			script = scriptCriaProfessor + ", (c:Curso{nome: '"+curso+"'}), (pr)-[:LECIONA]->(c)";
		}
		
		System.out.println(script);
		
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
	 * Realiza uma busca completa por todos os professores cadastrados 
	 * no banco de dados.
	 * 
	 * Retorna uma lista de objetos Professor.
	 */
	public List<Professor> listar() {
		//TODO Implementar a busca por professores.
		return null;
	}
	
	
	/**
	 * Busca no banco de dados um professor específico baseado no seu email e senha.
	 * 
	 * @param email
	 * @param senha
	 * @return Objeto Professor.
	 */
	public Professor buscarProfessor(String email, String senha){
		
		super.iniciaSessaoNeo4J();
		//transaction = session.beginTransaction();
		Professor professor = null;		
		
		String script = "MATCH(pr:Professor)-[:LECIONA]->(c:Curso) "
						+ "WHERE pr.email='"+email+"' AND pr.senha = '"+senha+"' "
						+ "return pr.nome as nome, pr.documentoRG as rg,"
						+ "pr.documentoCPF as cpf, pr.estadoCivil as esci,"
						+ "pr.matricula as mat, pr.senha as senha, pr.sexo as sexo,"
						+ "pr.titulacao as titulacao, pr.email as email, pr.site as site,"
						+ "pr.skype as skype, pr.telefone as telefone, "
						+ "pr.bairro as bairro, pr.dataAdmissao as dataadm, "
						+ "pr.dataNascimento as datanas, pr.cidade as cidade,"
						+ "pr.estado as estado, pr.rua as rua, c.nome as Curso";
	
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			professor = new Professor();
			Record registro = resultado.next();
			
			professor.setDocumentoRG(registro.get("rg").asString());
			professor.setDocumentoCPF(registro.get("cpf").asString());
			professor.setEstadoCivil(registro.get("esci").asString());
			professor.setMatricula(registro.get("mat").asString());
			professor.setNome(registro.get("nome").asString());
			professor.setSenha(registro.get("senha").asString());			
			professor.setSexo(registro.get("sexo").asString());
			professor.setTitulacao(registro.get("titulacao").asString());
			professor.getContato().setEmail(registro.get("email").asString());			
			professor.getContato().setSite(registro.get("site").asString());			
			professor.getContato().setSkype(registro.get("skype").asString());
			professor.getContato().setTelefone(registro.get("telefone").asString());
			professor.setDataNascimento(registro.get("datanas").asString());
			professor.setDataAdmissao(registro.get("dataadm").asString());
			professor.getEndereco().setCidade(registro.get("cidade").asString());	
			professor.getEndereco().setBairro(registro.get("bairro").asString());
			professor.getEndereco().setEstado(registro.get("estado").asString());
			professor.getEndereco().setRua(registro.get("rua").asString());
			professor.getCurso().setNome(registro.get("Curso").asString());
		}

		session.close();
		
		return professor;
	}
	
	
	
	
	/**
	 * Exclui do banco de dados o registro de um professor expecífico.
	 * 
	 * Esse método é necessário caso o professor queira deletar sua conta no sistema.
	 */
	public boolean excluir(Professor obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		return false;
	}
	

}
