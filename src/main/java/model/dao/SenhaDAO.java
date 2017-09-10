package model.dao;

import org.neo4j.driver.v1.exceptions.ClientException;
import model.pojo.Pessoa;

/**
 * Classe de acesso ao banco de dados para a altera��o de senha.
 * 
 * @author Luiz Eduardo
 */
public class SenhaDAO extends DAOBase {

	public SenhaDAO() { }
	
	/**
	 * Altera a senha do professor no banco de dados.
	 * Exige como par�metro o objeto da Pessoa logada no sistema
	 * e a nova senha. Retorna o status da altera��o da senha.
	 * 
	 * @param usuarioLogado
	 * @param novaSenha
	 * @return Status da altera��o de senha.
	 */
	public boolean alterarSenha(Pessoa usuarioLogado, String novaSenha){
		super.iniciaSessaoNeo4J();
		
		String email = usuarioLogado.getContato().getEmail();
		String senha = usuarioLogado.getSenha();
		
		transaction = session.beginTransaction();
		
		String script = "MATCH (n:Pessoa) WHERE n.email = '" +email+ "'AND n.senha ='" +senha+ "' "
				+ "SET n+= {senha:'" +novaSenha+ "'} RETURN n";
		
		boolean status = false;//Status do cadastro.
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
