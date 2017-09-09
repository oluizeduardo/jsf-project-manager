package model.dao;

import model.pojo.Pessoa;

/**
 * Classe de acesso ao banco de dados para a alteração de senha.
 * 
 * @author Luiz Eduardo
 */
public class SenhaDAO {

	
	
	public SenhaDAO() { }
	
	
	
	/**
	 * Altera a senha do professor no banco de dados.
	 * Exige como parâmetro o objeto da Pessoa logada no sistema
	 * e a nova senha. Retorna o status da alteração da senha.
	 * 
	 * @param usuarioLogado
	 * @param novaSenha
	 * @return Status da alteração de senha.
	 */
	public boolean alterarSenha(Pessoa usuarioLogado, String novaSenha){
		//TODO Contruir o método para alterar a senha do professor.
		return false;
	}
}
