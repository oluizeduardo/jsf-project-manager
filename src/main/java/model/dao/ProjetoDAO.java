package model.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.exceptions.ClientException;

import model.pojo.Pessoa;
import model.pojo.Projeto;
import web.SessionUtil;

public class ProjetoDAO extends DAOBase implements AcoesBancoDeDados<Projeto> {

	public ProjetoDAO() { }
	
	public boolean salvar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
		
		Pessoa usuarioLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		String email = usuarioLogado.getContato().getEmail();
		String senha = usuarioLogado.getSenha();
		
		System.out.println(email);
		System.out.println(senha);
		
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "MATCH (pr:Professor{email:'"+email+"',senha:'"+senha+"'}) CREATE (pj:Projeto {titulo: '" + projeto.getTitulo() 
		+ "', dataInicio:'" + projeto.getDataInicio() 
		+ "', dataPublicacao:'" + projeto.getDataPublicacao()
		+ "', eFinanciado:'" + projeto.getFinanciamento().isExistente()
		+ "', valor:'" + projeto.getFinanciamento().getValor()
		+ "', descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "', categoria:'" + projeto.getCategoria()
		+ "', numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		//+ "', cursosAlvos:'" + projeto.getCursosEnvolvidos()
		+ "', resumo:'" + projeto.getResumo()
		+ "', dataFim:'" + projeto.getDataFim()
		+ "'}),(pr)-[:COOORDENA{Desde:'"+projeto.getDataInicio()+"'}]->(pj)";
				
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

	public List<Projeto> listar() {
		
		super.iniciaSessaoNeo4J();
		
		ArrayList<Projeto>projetos = new ArrayList<Projeto>();
		
		StatementResult resultado = session.run("MATCH(pj:Projeto) return ID (pj) as id, pj.dataFim as Data_Fim, "
				+ "pj.dataInicio as Data_Inicio, pj.nomeCoordenador as Nome_Coordenador, "
				+ "pj.titulacaoCoordenador as Titulacao_Coordenador, pj.dataPublicacao as Data_Publicacao, "
				+ "pj.valor as Valor, pj.descricaoCurta as Descricao, pj.categoria as Categoria, "
				+ "pj.numeroParticipantes as Numero_Participantes, pj.resumo as Resumo, pj.titulo as Titulo");
		
		while(resultado.hasNext()) {
			
			Record projetoAtual = resultado.next();
			
			Projeto projetoAux = new Projeto();
			
			projetoAux.setDataFim(projetoAtual.get("Data_Fim").asString());
			projetoAux.setDataInicio(projetoAtual.get("Data_Inicio").asString());
			projetoAux.getCoordenador().setNome(projetoAtual.get("Nome_Coordenador").asString());
			projetoAux.getCoordenador().setTitulacao(projetoAtual.get("Titulacao_Coordenador").asString());
			projetoAux.setDataPublicacao(projetoAtual.get("Data_Publicacao").asString());
			projetoAux.getFinanciamento().setValor(projetoAtual.get("Valor").asDouble());
			projetoAux.setDescricaoCurta(projetoAtual.get("Descricao").asString());
			projetoAux.setCategoria(projetoAtual.get("Categoria").asString());
			projetoAux.setNumeroDeParticipantes(projetoAtual.get("Numero_Participantes").asInt());
			projetoAux.setResumo(projetoAtual.get("Resumo").asString());
			projetoAux.setTitulo(projetoAtual.get("Titulo").asString());
			
		}
		
		return projetos;
	}
	

	public void excluir(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
	}

	public void atualizar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (pj:Projeto) WHERE pj.titulo = '" + projeto.getTitulo()
		+ "' SET pj.titulo:'" + projeto.getTitulo()
		+ "'pj.dataFim: '" + projeto.getDataFim()
		+ "pj.dataInicio: '" + projeto.getDataInicio()
		+ "'pj.dataPublicacao:'" + projeto.getDataPublicacao()
		+ "'pj.eFinanciado:'" + projeto.getFinanciamento().isExistente()
		+ "'pj.valor:'" + projeto.getFinanciamento().getValor()
		+ "'pj.descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "'pj.categoria:'" + projeto.getCategoria()
		+ "'pj.numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		+ "'pj.resumo:'" + projeto.getResumo()
		+"'RETURN pj";
		
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
	
}
