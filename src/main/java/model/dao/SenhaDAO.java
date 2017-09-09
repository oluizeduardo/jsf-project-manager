package model.dao;

import model.pojo.Pessoa;

/**
 * Classe de acesso ao banco de dados para a altera��o de senha.
 * 
 * @author Luiz Eduardo
 */
public class SenhaDAO {

	
	
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
		//TODO Contruir o m�todo para alterar a senha do professor.
		return false;
	}
}
