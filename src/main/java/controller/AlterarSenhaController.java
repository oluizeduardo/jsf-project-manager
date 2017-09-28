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
	// Repetir a nova senha.
	private String confirmarSenha;

	
	
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
				// Atualiza a senha do usu�rio na Session.
				atualizaUsuarioDaSessao();
				limparCampos();
			}else{
				Mensagem.ExibeMensagemErro("Erro na altera��o de senha!");
			}
		}else {
			Mensagem.ExibeMensagemErro("A senha digitada n�o corresponde com a senha usada no login!");
		}
	}

	
	
	/**
	 * Atualiza a senha do usu�rio que est� na Session atual.
	 */
	private void atualizaUsuarioDaSessao(){
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
		// Atualiza o usu�rio da sess�o com a nova senha.
		pessoa.setSenha(novaSenha);

		// Desfaz a sess�o atual e constroi outra com os dados atualizados.
		SessionUtil.invalidate();
		SessionUtil.setParam(SessionUtil.KEY_SESSION, pessoa);
	}
	
	
	/**
	 * Atribui uma String em branco para limpar os campos da tela.
	 */
	private void limparCampos(){
		setSenhaAntiga("");
		setNovaSenha("");
		setConfirmarSenha("");
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
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	

}
