package model.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Aluno;
import model.pojo.Curso;
import model.pojo.Habilidade;
import model.pojo.Pessoa;
import model.pojo.Projeto;

public class AlunoDAO extends DAOBase implements AcoesBancoDeDados<Aluno> {

	
	// LIsta de projetos recomendados ao aluno.
	private List<Projeto> projetosRecomendados = null;
	
	
	
	public AlunoDAO() { }

	
	
	/**
	 * Salva no banco de dados o registro de um aluno.
	 * 
	 * O nó deve ser criado mesmo com as propriedades em branco,
	 * as mesmas serão preenchidas quando o aluno atualizar o perfil.
	 */
	public boolean salvar(Aluno aluno) {
		
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "CREATE (a:Aluno {nome: '" + aluno.getNome() 
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
		+ "', rua:'" + aluno.getEndereco().getRua() + "'})-"
		+ "[:CURSA]->(c:Curso{nome:'"+aluno.getCurso().getNome()+"'})";
		

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
	 * Retorna uma lista de alunos indicados para trabalhar nos projetos de um determinado professor.
	 * @param donoDoProjeto
	 * @return
	 */
	public List<Aluno> getAlunosIndicadosParaProfessor(Pessoa donoDoProjeto){
		List<Aluno> alunosindicados = new ArrayList<Aluno>();
		String email = donoDoProjeto.getContato().getEmail();
		String senha = donoDoProjeto.getSenha();
		
		String script = "MATCH (pro:Professor)-[:COORDENA]->(p:Projeto)-[:EXIGE]->"
				+ "(h:Habilidade)<-[:CONHECE]-(a:Aluno)-[:CURSA]->(c:Curso) "
				+ "WHERE not((a)-[:PARTICIPA]->(p)) AND pro.email='"+email+"' "
				+ "AND pro.senha='"+senha+"' RETURN a.nome as Aluno, c.nome as Curso, "
				+ "p.titulo as Projeto";
		
		super.iniciaSessaoNeo4J();		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record registro = resultado.next();
			Aluno alunoindicado = new Aluno();
			alunoindicado.setNome(registro.get("Aluno").asString());
			alunoindicado.setCurso(new Curso(registro.get("Curso").asString()));
			
			Projeto projetoIndicado = new Projeto();
			projetoIndicado.setTitulo(registro.get("Projeto").asString());
			alunoindicado.setProjetoIndicado(projetoIndicado);
			
			if(!verificaIndicacaoDeAlunoParaProjeto(alunosindicados, alunoindicado)){
				alunosindicados.add(alunoindicado);
			}
		}
		return alunosindicados;
	}
	
	
	/**
	 * Verifica na lista de alunos selecionados se já não existe um determinado aluno
	 * indicado para um determinado projeto.
	 * 
	 * @param indicados Lista dos alunos indicados.
	 * @param indicado Aluno indicado.
	 * @return true ou false.
	 */
	private boolean verificaIndicacaoDeAlunoParaProjeto(List<Aluno> indicados, Aluno indicado){
		for (Aluno aluno : indicados) {
			if(aluno.equals(indicado) ){
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Monta uma lista com todos os alunos cadastrados no banco de dados.
	 */
	public List<Aluno> listar() {
				
		super.iniciaSessaoNeo4J();
		
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		String script = "MATCH (a:Aluno)-[:CURSA]->(c:Curso) "
				+ "RETURN ID (a) as id, a.nome as Nome, "
				+ "c.nome as Curso, "
				+ "a.cidade as Cidade, a.estado as Estado, "
				+ "a.telefone as Telefone, a.site as Site, "
				+ "a.estadoCivil as Estado_Civil, a.senha as Senha, "
				+ "a.skype as Skype, "
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
			alunoAux.setCurso(new Curso(alunoAtual.get("Curso").asString()));
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
	 * Retorna uma lista contendo todas as habilidades de um determinado aluno.
	 */
	public List<Habilidade> listaHabilidadesDoAluno(String email, String senha){
		return new HabilidadeDAO().listarHabilidadesDoAluno(email, senha);
	}
	
	
	
	
	
	/**
	 * Exclui do banco de dados o registro de um aluno.
	 */
	public boolean excluir(Aluno aluno) {
//		super.iniciaSessaoNeo4J();		
//		transaction = session.beginTransaction();
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
		String script1="", script2 = "";
		
		transaction = session.beginTransaction();
		
		script1 = "MATCH (a:Aluno) WHERE a.email = '" +email+ "'AND a.senha ='" +senha+ "' "
				+ "SET a+= {nome: '" + aluno.getNome()
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
				+ "', rua:'" + aluno.getEndereco().getRua() + "'} return a";
		
		String curso = aluno.getCurso().getNome();
		
		// Se existe o curso, buca o nó para gerar associação.
		if(existeCurso(curso)){
			script2 = "MATCH (a:Aluno) WHERE a.nome='"+aluno.getNome()+"' "
					+ "MATCH (c:Curso) WHERE c.nome = '"+curso+"' "
					+ "CREATE (a)-[:CURSA]->(c) return a, c";
		
		// Se não existir, um novo nó deve ser criado e associado ao aluno.
		}else{
			script2 = "MATCH (a:Aluno) WHERE a.nome='"+aluno.getNome()+"' "
					+ "CREATE (c:Curso{nome:'"+curso+"'}) "
					+ "CREATE (a)-[:CURSA]->(c) return a, c";
		}
		
		// Remove o aluno de qualquer curso que ele já esteja.
		// O aluno não pode estar crsando mais de um curso ao mesmo tempo.
		removeAlunoDoCurso(aluno);
		
		try{
			// Executa o script no banco de dados.
			transaction.run(script1);
			transaction.run(script2);
			transaction.success();
			transaction.close();
			
			status = true;

		}catch(Exception ex){
			status = false;	
			ex.printStackTrace();
		}		
		return status;	
	}

	
	
	/**
	 * Remove o aluno de qualquer curso que ele esteja cursando.
	 * O aluno não pode estar crsando mais de um curso ao mesmo tempo.
	 * 
	 * @param aluno
	 * @return Status da exclusão.
	 */
	private boolean removeAlunoDoCurso(Aluno aluno){
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();
		boolean status = false;
		
		String script = "MATCH (a:Aluno)-[c:CURSA]->(:Curso) DELETE c";
		
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
	 * Busca no banco de dados um aluno específico baseado no seu email e senha.
	 * 
	 * @param email
	 * @param senha
	 * @return O objeto Aluno específico.
	 */
	public Aluno buscarAluno(String email, String senha) {
		
		super.iniciaSessaoNeo4J();
		Aluno aluno = null;
		
		String script = "MATCH(al:Aluno)-[:CURSA]->(c:Curso) "
				+ "WHERE al.email='"+email+"' AND al.senha = '"+senha+"' "
				+ "return al.nome as nome, al.documentoRG as rg,"
				+ "al.documentoCPF as cpf, al.estadoCivil as esci, "
				+ "al.matricula as mat, al.senha as senha, al.sexo as sexo, "
				+ "al.email as email, al.site as site, "
				+ "al.skype as skype, al.telefone as telefone, "
				+ "al.dataNascimento as datanas, al.cidade as cidade,"
				+ "al.estado as estado, al.rua as rua, c.nome as curso";
		
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
			aluno.setCurso(new Curso(registro.get("curso").asString()));
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
	public List<Projeto> getProjetosQueParticipa(Aluno aluno){
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto> projetosQueParticipa = new ArrayList<Projeto>();
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		
		
		String script = "MATCH(a:Aluno)-[:PARTICIPA]->(pj:Projeto)<-[:COORDENA]-(pr:Professor) "
				+ "WHERE a.email= '"+email+"' AND a.senha = '"+senha+"' return "
				+ "pj.titulo as Titulo, "
				+ "pj.dataPublicacao as Publicacao, "
				+ "pj.categoria as Categoria, "
				+ "pr.nome as Coordenador, "
				+ "pj.dataFim as Data_Fim, "
				+ "pj.dataInicio as Data_Inicio, "				
				+ "toFloat(pj.valor) as Valor, "
				+ "pj.descricaoCurta as Descricao, "				
				+ "toInteger(pj.numeroParticipantes) as QTD_Participantes, "
				+ "pj.resumo as Resumo, pj.eFinanciado as ehFinanciado";
		
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Publicacao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Coordenador").asString());
			projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asFloat());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setNumeroDeParticipantes(projetoAtual.get("QTD_Participantes").asInt());
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
		//	projetoAux.getFinanciamento().setExistente(projetoAtual.get("ehFinanciado").asBoolean());
			
			projetosQueParticipa.add(projetoAux);
			
		}
		
		return projetosQueParticipa;
	}
	
	
	
	
	/**
 	 * Verifica se na lista de projetos recomendados já não existe
 	 * tal projeto.
 	 * Necessário para não sugir ao usuário o mesmo projeto mais de uma vez.
 	 * 
 	 * Compara os projetos por título e nome do coordenador.
 	 * 
 	 * @param projeto O projeto que se deseja verificar a existência na lista.
 	 * @return verdadeiro ou falso sobre a existência do projeto.
 	 */
 	private boolean verificaExistenciaDeProjetoNaLista(Projeto projeto){		      
 		for (Projeto proj : projetosRecomendados) {
			if(proj.getTitulo().equals(projeto.getTitulo())){				
				if(proj.getCoordenador().getNome().equals(projeto.getCoordenador().getNome())){
					return true;
				}
			}
		}
 		return false;
 	}
	
	
	
	
	
	/**
	 * Retorna uma lista de projetos recomendados para o aluno.
	 * A busca por projetos é feita baseado nas habilidades que o aluno tem
	 * em comum com as habilidades exigidas nos projetos.
	 * 
	 * @param aluno
	 * @param todosProjetos
	 * @return Lista de projetos recomendados ao aluno.
	 */
	public List<Projeto> getProjetosRecomendados(Aluno aluno, List<Projeto> todosProjetos){
		super.iniciaSessaoNeo4J();
				
		this.projetosRecomendados = new ArrayList<Projeto>();
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		
		String script = "MATCH(pr:Professor)-[:COORDENA]->(pj:Projeto)-[:EXIGE]->"
					  + "(h:Habilidade)<-[:CONHECE]-(a:Aluno) "
					  + "WHERE not((a)-[:PARTICIPA]-(pj)) "
					  + "AND a.email='"+email+"' AND a.senha='"+senha+"' "
					  + "return a.nome as Aluno, "
					  + "pr.nome as Coordenador, "
					  + "pj.categoria as Categoria, "
					  + "pj.dataPublicacao as Publicacao, "
					  + "pj.dataInicio as DataInicio, "
					  + "pj.dataFim as DataFim, "
					  + "pj.descricaoCurta as Descricao, "
					  + "pj.resumo as Resumo, "
					  + "pj.titulo as Titulo";
		
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			
			Record projetoLocalizado = resultado.next();
			
			Projeto projeto = new Projeto();
			
			projeto.setTitulo(projetoLocalizado.get("Titulo").asString());
			projeto.setCategoria(projetoLocalizado.get("Categoria").asString());
			projeto.setDataPublicacao(projetoLocalizado.get("Publicacao").asString());
			projeto.setDataInicio(projetoLocalizado.get("DataInicio").asString());
			projeto.setDataFim(projetoLocalizado.get("DataFim").asString());
			projeto.setDescricaoCurta(projetoLocalizado.get("Descricao").asString());
			projeto.setResumo(projetoLocalizado.get("Resumo").asString());			
			projeto.getCoordenador().setNome(projetoLocalizado.get("Coordenador").asString());

			if(!verificaExistenciaDeProjetoNaLista(projeto)){
				projetosRecomendados.add(projeto);
			}					
		}
		session.close();
		
		return projetosRecomendados;
	}
	
	
	
	/**
	 * Verifica no banco de dados se o aluno já participa do projeto.
	 * 
	 * @param aluno
	 * @param projeto
	 * @return verdadeiro ou falso.
	 */
	public boolean verificaParticipacaoEmProjeto(Aluno aluno, Projeto projeto){

		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		String tituloProjeto = projeto.getTitulo();
		
		String script = "MATCH(a:Aluno)-[:PARTICIPA]->(p:Projeto) "
					+ "WHERE a.email='"+email+"' "
					+ "AND a.senha='"+senha+"' "
					+ "AND p.titulo='"+tituloProjeto+"' "
					+ "return a, p";
		
		super.iniciaSessaoNeo4J();
		StatementResult resultado = session.run(script);
		
		while(resultado.hasNext()) {
			return true;
		}		
		return false;
	}
	
	
	
	
	
	/**
	 * Relaciona o aluno logado com o projeto em que deseja se candidatar.
	 * 
	 * @param aluno
	 * @param projeto
	 * @return boolean indicando o status da candidatura.
	 */
	public boolean candidatar(Aluno aluno, Projeto projeto) {
		
		String email = aluno.getContato().getEmail();
		String senha = aluno.getSenha();
		String tituloProjeto = projeto.getTitulo();
		boolean status = false;
		
		super.iniciaSessaoNeo4J();
		transaction = session.beginTransaction();
		
		String script = "MATCH (a:Aluno) where a.email='"+email+"'AND a.senha='"+senha+"'"
				+ "MATCH (pj:Projeto) WHERE pj.titulo='"+tituloProjeto+"' "
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
