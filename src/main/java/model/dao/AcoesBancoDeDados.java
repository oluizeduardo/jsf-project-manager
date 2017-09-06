package model.dao;

import java.util.List;

/**
 * Define os m�todos padr�es para as classes DAO.
 *
 * @param <C>
 */
public interface AcoesBancoDeDados <C> {

	void atualizar(C obj);
	
	boolean salvar(C obj);
	
	List<C> listar();
	
	void excluir(C obj);
}
