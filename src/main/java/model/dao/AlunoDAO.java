package model.dao;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
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
	public void atualizarAluno(Aluno aluno) {
		Transaction tx = session.beginTransaction();

		try {
			System.out.println("CHEGOU AQUI!!");
			System.out.println("aluno nome: " + aluno.getNome());
			
			tx.run("MATCH (a:Aluno) WHERE a.nome = '" + aluno.getNome() + 
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
					+ "' RETURN a");
			tx.success();
		} finally {
			try {
				tx.close();
			} catch (ClientException excep) {
				tx.failure();
				tx.close();
			}
		}

		session.close();

	}
	
	//método para localizar aluno específico por usuário e senha
	public Aluno buscaAlunoEspecifico(String email) {
		System.out.println("BUSCA POR ALUNO ESPECIFICO");
		
		Aluno alunoEspecifico = new Aluno();
		
		StatementResult resultado = session.run("MATCH (a:Aluno) WHERE a.email= '" + email + "' RETURN a.email as email");
		
		while(resultado.hasNext()) {
			
			Record registro = resultado.next();
			
			String email2 = registro.get("email").asString();
			
			System.out.println(email2);
			
			Aluno alunoAux = new Aluno();
			alunoAux.getContato().setEmail(email2);
			alunoEspecifico = alunoAux;
		}
		
		session.close();
		return alunoEspecifico;
	}
		
}
