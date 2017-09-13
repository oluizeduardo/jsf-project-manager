package model.dao;

import java.util.List;

/**
 * Define os métodos padrões para as classes DAO.
 *
 * @param <C>
 */
public interface AcoesBancoDeDados <C> {

	boolean atualizar(C obj);
	
	boolean salvar(C obj);
	
	List<C> listar();
	
	boolean excluir(C obj);
}
