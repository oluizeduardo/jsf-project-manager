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
	
	// A mesma senha que foi usada para acessar o sistema.
	private String senhaAntiga;
	// A nova senha que se deseja utilizar.
	private String novaSenha;

	
	
	public AlterarSenhaController() { }

	
	/**
	 * Executado quando o usu�rio deseja alterar a senha de acesso ao sistema.
	 */
	public void alterarSenha(){		
		// Retorna os dados da pessoa logada nos sistema.
		Pessoa usuarioLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		
		if(senhaAntiga.equals(usuarioLogado.getSenha())) {
			
			boolean alterouSenha = new SenhaDAO().alterarSenha(usuarioLogado, novaSenha);
			
			if(alterouSenha) {
				Mensagem.ExibeMensagem("Senha alterada com sucesso!");			
			}else{
				Mensagem.ExibeMensagemErro("Erro na altera��o de senha!");
			}
		}else {
			Mensagem.ExibeMensagemErro("A senha digitada n�o corresponde com a senha usada no login!");
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
