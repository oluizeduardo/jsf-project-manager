package model.dao;

import java.util.List;
import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Projeto;

public class ProjetoDAO extends DAOBase implements AcoesBancoDeDados<Projeto> {

	public ProjetoDAO() { }
	
	public boolean salvar(Projeto projeto) {
		super.iniciaSessaoNeo4J();
				
		transaction = session.beginTransaction();
		boolean status = false;//Status do cadastro.
		
		String script = "CREATE (pj:Projeto {dataFim: '" + projeto.getDataFim() 
		+ "', dataInicio:'" + projeto.getDataInicio() 
		+ "', nomeCoordenador:'" + projeto.getCoordenador().getNome()
		+ "', titulacaoCoordenador:'" + projeto.getCoordenador().getTitulacao()
		+ "', dataPublicacao:'" + projeto.getDataPublicacao()
		+ "', valor:'" + projeto.getFinanciamento().getValor()
		+ "', descricaoCurta:'" + projeto.getDescricaoCurta()
		+ "', natureza:'" + projeto.getNatureza()
		+ "', numeroParticipantes:'" + projeto.getNumeroDeParticipantes()
		//+ "', cursosAlvos:'" + projeto.getCursosEnvolvidos()
		+ "', resumo:'" + projeto.getResumo()
		+ "', titulo:'" + projeto.getTitulo()
		+ "'})";
		
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
		// TODO Auto-generated method stub
		return null;
	}

	public void excluir(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		
	}

	public void atualizar(Projeto obj) {
		super.iniciaSessaoNeo4J();
		// TODO Auto-generated method stub
		
	}

}
