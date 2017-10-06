package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import model.pojo.Curso;
import model.pojo.Pessoa;

public class LoginDAO extends DAOBase {

	public LoginDAO() {
	}

	public Pessoa buscaPessoa(String email, String senha) {

		super.iniciaSessaoNeo4J();
		
		Pessoa pessoa = null;
		boolean encontrado = false;// Usuário encontrado.
		
		String script = "MATCH (n)-[]-(c:Curso) "
				+ " WHERE n.email= '" + email + "'and n.senha = '" + senha
				+ "' RETURN n.email as email, "
				+ "n.senha as senha, n.papel as papel, n.cidade as cidade,"
				+ "n.nome as nome, n.documentoRG as rg, n.documentoCPF as cpf,"
				+ "n.sexo as sexo, n.estadoCivil as ec, n.dataNascimento as dataNas,"
				+ "n.matricula as matricula, n.bairro as bairro, n.estado as estado,"
				+ "c.nome as curso, n.site as site, n.skype as skype, "
				+ "n.telefone as tel";

		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			encontrado = true;
			pessoa = new Pessoa();
			Record registro = resultado.next();
			
			pessoa.setId(registro.get("id"));
			pessoa.getContato().setEmail(registro.get("email").asString());
			pessoa.setSenha(registro.get("senha").asString());
			pessoa.setPapel(registro.get("papel").asString());
			pessoa.getEndereco().setCidade(registro.get("cidade").asString());			
			pessoa.setNome(registro.get("nome").asString());			
			pessoa.setDocumentoRG(registro.get("rg").asString());
			pessoa.setDocumentoCPF(registro.get("cpf").asString());
			pessoa.setSexo(registro.get("sexo").asString());			
			pessoa.setEstadoCivil(registro.get("ec").asString());
			pessoa.setDataNascimento(registro.get("dataNas").asString());
			pessoa.setMatricula(registro.get("matricula").asString());			
			pessoa.getEndereco().setBairro(registro.get("bairro").asString());
			pessoa.getEndereco().setEstado(registro.get("estado").asString());
			pessoa.setCurso(new Curso(registro.get("curso").asString()));
			pessoa.getContato().setSite(registro.get("site").asString());
			pessoa.getContato().setSkype(registro.get("skype").asString());
			pessoa.getContato().setTelefone(registro.get("tel").asString());
		}
		
		// Se não foi encontrado nenhum usuário associado a um curso,
		// a opção é procurar sem estar associado a um curso.
		if(!encontrado){
			script = "MATCH (n) "
					+ " WHERE n.email= '" + email + "'and n.senha = '" + senha
					+ "' RETURN n.email as email, "
					+ "n.senha as senha, n.papel as papel, n.cidade as cidade,"
					+ "n.nome as nome, n.documentoRG as rg, n.documentoCPF as cpf,"
					+ "n.sexo as sexo, n.estadoCivil as ec, n.dataNascimento as dataNas,"
					+ "n.matricula as matricula, n.bairro as bairro, n.estado as estado,"
					+ "n.site as site, n.skype as skype, "
					+ "n.telefone as tel";

			resultado = session.run(script);
			
			while (resultado.hasNext()) {
				pessoa = new Pessoa();
				Record registro = resultado.next();
				
				pessoa.getContato().setEmail(registro.get("email").asString());
				pessoa.setSenha(registro.get("senha").asString());
				pessoa.setPapel(registro.get("papel").asString());
				pessoa.getEndereco().setCidade(registro.get("cidade").asString());			
				pessoa.setNome(registro.get("nome").asString());			
				pessoa.setDocumentoRG(registro.get("rg").asString());
				pessoa.setDocumentoCPF(registro.get("cpf").asString());
				pessoa.setSexo(registro.get("sexo").asString());			
				pessoa.setEstadoCivil(registro.get("ec").asString());
				pessoa.setDataNascimento(registro.get("dataNas").asString());
				pessoa.setMatricula(registro.get("matricula").asString());			
				pessoa.getEndereco().setBairro(registro.get("bairro").asString());
				pessoa.getEndereco().setEstado(registro.get("estado").asString());
				pessoa.getContato().setSite(registro.get("site").asString());
				pessoa.getContato().setSkype(registro.get("skype").asString());
				pessoa.getContato().setTelefone(registro.get("tel").asString());
			}
		}
		session.close();
		
		return pessoa;
	}

}
