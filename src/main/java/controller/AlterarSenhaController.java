package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.dao.SenhaDAO;
import model.pojo.Pessoa;
import view.Mensagem;
import web.SessionUtil;

/*
 * Quando um m�todo da classe ManagedBean n�o tiver sendo acessado,
 * deve-se tentar resolver com os seguintes passos:
 * - Na anota��o @ManagedBean remover o name;
 * - Colocar a anota��o @SessionScoped;
 * - Implementar a interface Serializable;
 * - Criar um contrutor default para a classe Bean;
 * - Criar m�todos get e set para as propriedades da classe.
 */
@ManagedBean
@SessionScoped
public class AlterarSenhaController implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String senhaAntiga;
	private String novaSenha;
	
	
	public AlterarSenhaController() { }
	
	
	public void alterarSenha(){
		System.out.println("Alterando a senha do professor...\n"
				+ "SENHA ANTIGA: "+senhaAntiga+"\nNOVA SENHA: "+novaSenha);
		
		// Retorna os dados da pessoa logada nos sistema.
		Pessoa usuarioLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		// Retorna o status da altera��o da senha.
		boolean alterouSenha = new SenhaDAO().alterarSenha(usuarioLogado, novaSenha);
	
		if(alterouSenha){
			System.out.println("Senha alterada com sucesso!");
			Mensagem.ExibeMensagem("Senha alterada com sucesso!");
		}else{
			System.out.println("Erro na altera��o de senha!");
			Mensagem.ExibeMensagemErro("Erro na altera��o de senha!");
		}
	}

	
	
	
	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	
}
