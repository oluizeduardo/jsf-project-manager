package model.dao;

import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Aluno;



public class AlunoDAO extends DAOBase implements AcoesBancoDeDados<Aluno> {
		
	
	
	public AlunoDAO() { }
	
	
	
	public boolean salvar(Aluno aluno) {
		
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "CREATE (a:Aluno {nome: '" + aluno.getNome() 
		+ "', curso:'" + aluno.getCurso() 
		+ "', dataMatricula:'" + aluno.getDataMatricula()
		+ "', documentoCPF:'" + aluno.getDocumentoCPF()
		+ "', documentoRG:'" + aluno.getDocumentoRG()
		+ "', estadoCivil:'" + aluno.getEstadoCivil()
		+ "', matricula:'" + aluno.getMatricula()
		+ "', senha:'" + aluno.getSenha()
		+ "', sexo:'" + aluno.getSexo()
		+ "', dataNascimento:'" + aluno.getDataNascimento()
		+ "', habilidades:'" + aluno.getHabilidades()
		+ "', idiomas:'" + aluno.getIdiomas()
		+ "', email:'" + aluno.getContato().getEmail()
		+ "', facebook:'" + aluno.getContato().getFacebook()
		+ "', skype:'" + aluno.getContato().getSkype()
		+ "', telefone:'" + aluno.getContato().getTelefone()
		+ "', bairro:'" + aluno.getEndereco().getBairro()
		+ "', cidade:'" + aluno.getEndereco().getCidade()
		+ "', estado:'" + aluno.getEndereco().getEstado()
		+ "', rua:'" + aluno.getEndereco().getRua() + "'})";
		
		
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

	
	
	public List<Aluno> listar() {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		return null;
	}

	public void excluir(Aluno obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		
	}
	

	
	
	public void atualizar(Aluno aluno) {
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (a:Aluno) WHERE a.nome = '" + aluno.getNome() + 
				"' SET a.nome = '" + aluno.getNome() + "', "
				+ "a.curso = '" + aluno.getCurso() + "', " 
				+ "a.dataMatricula = '" + aluno.getDataMatricula() + "', "
				+ "a.documentoCPF = '" + aluno.getDocumentoCPF() + "', "
				+ "a.documentoRG = '" + aluno.getDocumentoRG() + "', "
				+ "a.estadoCivil = '" + aluno.getEstadoCivil() + "', "
				+ "a.matricula = '" + aluno.getMatricula() + "', "
				+ "a.senha = '" + aluno.getSenha() + "', "
				+ "a.sexo = '" + aluno.getSexo() + "', "
				+ "a.dataNascimento = '" + aluno.getDataNascimento() + "', "
				+ "a.habilidades = '" + aluno.getHabilidades() + "', " 
				+ "a.idiomas = '" + aluno.getIdiomas() + "', "
				+ "a.email = '" + aluno.getContato().getEmail() + "', "
				+ "a.facebook = '" + aluno.getContato().getFacebook() + "', "
				+ "a.skype = '" + aluno.getContato().getSkype() + "', "
				+ "a.telefone = '" + aluno.getContato().getTelefone() + "', "
				+ "a.bairro = '" + aluno.getEndereco().getBairro() + "', "
				+ "a.cidade = '" + aluno.getEndereco().getCidade() + "', "
				+ "a.estado = '" + aluno.getEndereco().getEstado() + "', "
				+ "a.rua = '" + aluno.getEndereco().getRua() + "', "
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
	
	
	
	
	public Aluno buscarAlunoPorEmail(String email) {

		Aluno alunoEspecifico = new Aluno();
		String script = "MATCH (a:Aluno) WHERE a.email= '" + email + "' RETURN a.email as email";
		
		StatementResult resultado = session.run(script);

		while (resultado.hasNext()) {

			Record registro = resultado.next();

			String email2 = registro.get("email").asString();
			
			alunoEspecifico.getContato().setEmail(email2);
		}

		session.close();
		return alunoEspecifico;
	}
	

		
}
