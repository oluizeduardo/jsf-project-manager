package model.dao;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.ClientException;

import model.dao.databaseconfig.ConnectionNeo4j;
import model.pojo.Aluno;

public class AlunoDAO {
	
	private Session session = null;
	
	public AlunoDAO() {
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
	//Método para salvar (criar um nó) do tipo aluno no banco
	public void salvarAluno (Aluno aluno) {
		System.out.println("Objeto aluno:" + aluno);
		Transaction tx = session.beginTransaction();
		
		try{
			tx.run("CREATE (a:Aluno {nome: '" + aluno.getNome() 
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
			+ "', rua:'" + aluno.getEndereco().getRua() + "'})");
			
			tx.success();
		}
		finally {
			try {
				tx.close();
			} 
			catch (ClientException excep) {
				tx.failure();
				tx.close();
			}
		}
		session.close();
	}
	
	//Método para atualizar o nó aluno no banco
	public void atualizarAluno() {
		
	}

}
