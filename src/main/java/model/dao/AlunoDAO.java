package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Aluno;
import model.pojo.Projeto;

public class AlunoDAO extends DAOBase implements AcoesBancoDeDados<Aluno> {

	
	
	public AlunoDAO() { }

	
	
	/**
	 * Salva no banco de dados o registro de um aluno.
	 */
	public boolean salvar(Aluno aluno) {
		
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "CREATE (a:Aluno {nome: '" + aluno.getNome() 
		+ "', curso:'" + aluno.getCurso() 
		+ "', papel:'" + aluno.getPapel()
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

	
	
	/**
	 * Monta uma lista com todos os alunos cadastrados no banco de dados.
	 */
	public List<Aluno> listar() {
				
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

	
	
	/**
	 * Exclui do banco de dados o registro de um aluno.
	 */
	public boolean excluir(Aluno aluno) {
		super.iniciaSessaoNeo4J();		
		transaction = session.beginTransaction();
		return false;
	}
	
	
	/**
	 * Atualiza no banco de dados o registro de um aluno.
	 */
	public boolean atualizar(Aluno aluno) {
		super.iniciaSessaoNeo4J();
		
		// Dados do usuário logado no sistema.
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		boolean status = false;// Status da atualização.		
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (n:Aluno) WHERE n.email = '" +email+ "'AND n.senha ='" +senha+ "' "
				+ "SET n+= {nome: '" + aluno.getNome()
				+ "', curso:'" + aluno.getCurso() 
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
				+ "', rua:'" + aluno.getEndereco().getRua() + "'} RETURN n";
				
		try{
			// Executa o script no banco de dados.
			transaction.run(script);			
			transaction.success();
			transaction.close();
			session.close();
			
			status = true;

		}catch(Exception ex){
			status = false;	
		}
		
		return status;	
	}

	
	
	/**
	 * Busca no banco de dados um aluno específico baseado no seu email e senha.
	 * 
	 * @param email
	 * @param senha
	 * @return O objeto Aluno específico.
	 */
	public Aluno buscarAluno(String email, String senha) {
		
		super.iniciaSessaoNeo4J();
		Aluno aluno = null;
		
		String script = "MATCH(al:Aluno) "
				+ "WHERE al.email='"+email+"' AND al.senha = '"+senha+"' "
				+ "return al.nome as nome, al.documentoRG as rg,"
				+ "al.documentoCPF as cpf, al.estadoCivil as esci, "
				+ "al.matricula as mat, al.senha as senha, al.sexo as sexo, "
				+ "al.email as email, al.site as site, "
				+ "al.skype as skype, al.telefone as telefone, "
				+ "al.dataNascimento as datanas, al.cidade as cidade,"
				+ "al.estado as estado, al.rua as rua, al.curso as curso";
		
		StatementResult resultado = session.run(script);
		
		while (resultado.hasNext()) {
			aluno = new Aluno();
			Record registro = resultado.next();
			
			aluno.setNome(registro.get("nome").asString());
			aluno.setDocumentoRG(registro.get("rg").asString());
			aluno.setDocumentoCPF(registro.get("cpf").asString());
			aluno.setEstadoCivil(registro.get("esci").asString());			
			aluno.setMatricula(registro.get("mat").asString());
			aluno.setSenha(registro.get("senha").asString());			
			aluno.setSexo(registro.get("sexo").asString());
			aluno.getContato().setEmail(registro.get("email").asString());			
			aluno.getContato().setSite(registro.get("site").asString());			
			aluno.getContato().setSkype(registro.get("skype").asString());
			aluno.getContato().setTelefone(registro.get("telefone").asString());
			aluno.setDataNascimento(registro.get("datanas").asString());
			aluno.getEndereco().setCidade(registro.get("cidade").asString());			
			aluno.getEndereco().setEstado(registro.get("estado").asString());
			aluno.getEndereco().setRua(registro.get("rua").asString());
			aluno.setCurso(registro.get("curso").asString());
		}	
		
		aluno.setHabilidades(new HabilidadeDAO().listarHabilidadesDoAluno(email, senha));
		aluno.setIdiomas(new IdiomaDAO().listarIdiomasDoAluno(email, senha));
		
		return aluno;
	}
	
	
	
	/**
	 * Lista todos os projetos que o aluno logado está participando.
	 * 
	 * @param email
	 * @param senha
	 * @return Uma lista de objetos Projetos.
	 */
	public List<Projeto> getProjetosQueParticipa(String email, String senha){
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto> projetosQueParticipa = new ArrayList<Projeto>();
		
		
		String script = "MATCH(a:Aluno)-[:PARTICIPA]->(pj:Projeto)<-[:COOORDENA]-(pr:Professor) "
				+ "WHERE a.email= '"+email+"' AND a.senha = '"+senha+"' return id(pj) as ID, "
				+ "pj.titulo as Titulo, pj.dataFim as Data_Fim, "
				+ "pj.dataInicio as Data_Inicio, "
				+ "pj.dataPublicacao as Publicacao, "
				+ "pj.valor as Valor, pj.descricaoCurta as Descricao, "
				+ "pj.categoria as Categoria, "
				+ "pj.numeroParticipantes as QTD_Participantes, "
				+ "pj.resumo as Resumo, pj.eFinanciado as ehFinanciado, "
				+ "pr.nome as Coordenador";
		
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			
			projetosQueParticipa.add(projetoAux);
			
		}
		
		return projetosQueParticipa;
	}
	
	
	/**
	 * Relaciona o aluno logado com o projeto em que deseja se candidatar.
	 * 
	 * @param email
	 * @param senha
	 * @param projeto
	 * @return boolean indicando o status da candidatura.
	 */
	public boolean candidatar(String email, String senha, String projeto) {
		
		System.out.println("Candidatando ao projeto "+projeto+" EMAIL: "+email+" SENHA: "+senha);
		super.iniciaSessaoNeo4J();
		
		boolean status = false;
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (a:Aluno) where a.email='"+email+"'AND a.senha='"+senha+"'"
				+ "MATCH (pj:Projeto) WHERE pj.titulo='"+projeto+"' "
				+ "CREATE (a)-[:PARTICIPA]->(pj) return a,pj";
				
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
	
}
