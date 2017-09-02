package model.dao;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.dao.databaseconfig.ConnectionNeo4j;
import model.pojo.Professor;

public class ProfessorDAO {
	
private Session session = null;
	
	public ProfessorDAO() {
		ConnectionNeo4j neo4j = new ConnectionNeo4j();
		this.session = neo4j.getSession();
	}
	
	//Método para salvar (criar um nó) do tipo professor no banco
	public void salvarProfessor (Professor professor) {
		System.out.println("Objeto aluno:" + professor);
		Transaction tx = session.beginTransaction();
		
		try{
			tx.run("CREATE (pr:Professor {nome: '" + professor.getDataAdmissao() 
			+ "', documentoCPF:'" + professor.getDocumentoCPF() 
			+ "', documentoRG:'" + professor.getDocumentoRG()
			+ "', estadoCivil:'" + professor.getEstadoCivil()
			+ "', matricula:'" + professor.getMatricula()
			+ "', nome:'" + professor.getNome()
			+ "', profissao:'" + professor.getProfissao()
			+ "', senha:'" + professor.getSenha()
			+ "', sexo:'" + professor.getSexo()
			+ "', titulacao:'" + professor.getTitulacao()
			+ "', email:'" + professor.getContato().getEmail()
			+ "', facebook:'" + professor.getContato().getFacebook()
			+ "', skype:'" + professor.getContato().getSkype()
			+ "', telefone:'" + professor.getContato().getTelefone()
			+ "', dataNascimento:'" + professor.getDataNascimento()
			+ "', disciplinas:'" + professor.getDisciplinas()
			+ "', bairro:'" + professor.getEndereco().getBairro()
			+ "', cidade:'" + professor.getEndereco().getCidade()
			+ "', estado:'" + professor.getEndereco().getEstado()
			+ "', rua:'" + professor.getEndereco().getRua() + "'})");
			
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

}
