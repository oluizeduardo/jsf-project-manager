package model.dao;

import java.util.ArrayList;
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
		+ "', papel:'" + aluno.getPapel()
		+ "', dataMatricula:'" + aluno.getDataMatricula()
		+ "', documentoCPF:'" + aluno.getDocumentoCPF()
		+ "', documentoRG:'" + aluno.getDocumentoRG()
		+ "', estadoCivil:'" + aluno.getEstadoCivil()
		+ "', matricula:'" + aluno.getMatricula()
		+ "', senha:'" + aluno.getSenha()
		+ "', sexo:'" + aluno.getSexo()
		+ "', dataNascimento:'" + aluno.getDataNascimento()
		+ "', email:'" + aluno.getContato().getEmail()
		+ "', site:'" + aluno.getContato().getSite()
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
		
		//lista todos os alunos
		
		super.iniciaSessaoNeo4J();
		
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		String script = "MATCH (a:Aluno) RETURN ID (a) as id, a.nome as Nome, "
				+ "a.cidade as Cidade, a.estado as Estado, "
				+ "a.telefone as Telefone, a.site as Site, "
				+ "a.estadoCivil as Estado_Civil, a.senha as Senha, "
				+ "a.skype as Skype, a.curso as Curso, "
				+ "a.dataMatricula as Data_Matricula, a.matricula as Matricula, "
				+ "a.documentoCPF as CPF, a.documentoRG as RG, a.sexo as Sexo, "
				+ "a.dataNascimento as Data_Nascimento, "
				+ "a.papel as Papel, a.email as Email, a.rua as rua";
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record alunoAtual = resultado.next();
			
			Aluno alunoAux = new Aluno();
			
			alunoAux.setId(alunoAtual.get("id"));
			alunoAux.setNome(alunoAtual.get("Nome").asString());
			alunoAux.getEndereco().setCidade(alunoAtual.get("Cidade").asString());
			alunoAux.getEndereco().setEstado(alunoAtual.get("Estado").asString());
			alunoAux.getContato().setTelefone(alunoAtual.get("Telefone").asString());
			alunoAux.getContato().setSite(alunoAtual.get("Site").asString());
			alunoAux.setEstadoCivil(alunoAtual.get("Estado_Civil").asString());
			alunoAux.setSenha(alunoAtual.get("Senha").asString());
			alunoAux.getContato().setSkype(alunoAtual.get("Skype").asString());
			alunoAux.setCurso(alunoAtual.get("Curso").asString());
			alunoAux.setDataMatricula(alunoAtual.get("Data_Matricula").asString());
			alunoAux.setMatricula(alunoAtual.get("Matricula").asString());
			alunoAux.setDocumentoCPF(alunoAtual.get("CPF").asString());
			alunoAux.setDocumentoRG(alunoAtual.get("RG").asString());
			alunoAux.setSexo(alunoAtual.get("Sexo").asString());
			alunoAux.setDataNascimento(alunoAtual.get("Data_Nascimento").asString());
			alunoAux.setPapel(alunoAtual.get("Papel").asString());
			alunoAux.getContato().setEmail(alunoAtual.get("Email").asString());
			alunoAux.getEndereco().setRua(alunoAtual.get("Rua").asString());
			
			alunos.add(alunoAux);
		
		}
		
		return alunos;
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
				+ "a.email = '" + aluno.getContato().getEmail() + "', "
				+ "a.site = '" + aluno.getContato().getSite() + "', "
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

		iniciaSessaoNeo4J();
		
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
