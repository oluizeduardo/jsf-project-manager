package model.dao;

import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Professor;



public class ProfessorDAO extends DAOBase implements AcoesBancoDeDados<Professor> {
	
	
	
	public ProfessorDAO() { }
	
	
	/**
	 * Atualiza no banco de dados o registro de um Professor específico.
	 */
	public void atualizar(Professor professor) {
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (pr:Professor) WHERE a.email = '" + professor.getContato().getEmail() 
				+ "'and a.senha ='" + professor.getSenha() +

				"' SET a.nome = '" + professor.getNome() + "', "
				+ "', papel:'" + professor.getPapel()
				+ "', documentoCPF:'" + professor.getDocumentoCPF() 
				+ "', documentoRG:'" + professor.getDocumentoRG()
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', matricula:'" + professor.getMatricula()
				+ "', nome:'" + professor.getNome()
				+ "', senha:'" + professor.getSenha()
				+ "', sexo:'" + professor.getSexo()
				+ "', titulacao:'" + professor.getTitulacao()
				+ "', email:'" + professor.getContato().getEmail()
				+ "', site:'" + professor.getContato().getSite()
				+ "', skype:'" + professor.getContato().getSkype()
				+ "', telefone:'" + professor.getContato().getTelefone()
				+ "', dataNascimento:'" + professor.getDataNascimento()
				+ "', bairro:'" + professor.getEndereco().getBairro()
				+ "', cidade:'" + professor.getEndereco().getCidade()
				+ "', estado:'" + professor.getEndereco().getEstado()
				+ "', rua:'" + professor.getEndereco().getRua()
				+ "' RETURN a";
		
		try {
			// Executa o script no banco de dados.
			transaction.run(script);
			transaction.success();
			
		} finally {
			try {
				transaction.close();
			} catch (ClientException excep) {
				transaction.failure();
				transaction.close();
			}
		}
		session.close();	
	}
	
	
	
	/**
	 * Salva no banco de dados os dados de um Professor.
	 * 
	 * Retorna o status do cadastro no banco.
	 */
	public boolean salvar(Professor professor) {
		
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "CREATE (pr:Professor {nome: '" + professor.getNome()
				+ "', papel:'" + professor.getPapel()
				+ "', documentoCPF:'" + professor.getDocumentoCPF() 
				+ "', documentoRG:'" + professor.getDocumentoRG()
				+ "', estadoCivil:'" + professor.getEstadoCivil()
				+ "', matricula:'" + professor.getMatricula()
				+ "', dataAdmissao:'" + professor.getDataAdmissao()
				+ "', senha:'" + professor.getSenha()
				+ "', sexo:'" + professor.getSexo()
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
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
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
		
		String script = "MATCH(pr:Professor) "
						+ "WHERE pr.email='"+email+"' AND pr.senha = '"+senha+"' "
						+ "return pr.nome as nome, pr.documentoRG as rg,"
						+ "pr.documentoCPF as cpf, pr.estadoCivil as esci,"
						+ "pr.matricula as mat, pr.senha as senha, pr.sexo as sexo,"
						+ "pr.titulacao as titulacao, pr.email as email, pr.site as site,"
						+ "pr.skype as skype, pr.telefone as telefone, "
						+ "pr.dataNascimento as datanas, pr.cidade as cidade,"
						+ "pr.estado as estado, pr.rua as rua";
	
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
			professor.getEndereco().setCidade(registro.get("cidade").asString());			
			professor.getEndereco().setEstado(registro.get("estado").asString());
			professor.getEndereco().setRua(registro.get("rua").asString());					
		}

		session.close();
		
		return professor;
	}
	
	
	
	/**
	 * Exclui do banco de dados o registro de um professor expecífico.
	 * 
	 * Esse método é necessário caso o professor queira deletar sua conta no sistema.
	 */
	public void excluir(Professor obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
	}
	

}
