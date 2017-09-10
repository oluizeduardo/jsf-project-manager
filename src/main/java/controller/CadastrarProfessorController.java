package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.pojo.Pessoa;
import model.pojo.Professor;
import view.Mensagem;
import web.SessionUtil;
import model.dao.ProfessorDAO;
import model.helperView.ListaDeEstadoCivil;
import model.helperView.ListaDeEstados;


@ManagedBean(name = "cadastrarProfessorController")
@ViewScoped
public class CadastrarProfessorController {

	
	private Professor professor = null;
	private List<String> estadosBrasileiros = null;
	private List<String> estadoCivil = null;
	private Date dataNascimento = null;
	private Date dataAdmissao = null;
	
	
	
	public CadastrarProfessorController() {
		
		// Carrega os dados do professor logado no sistema.
		carregaDadosDoProfessor();
			
		this.estadosBrasileiros = new ListaDeEstados().getList();
		this.estadoCivil = new ListaDeEstadoCivil().getList();
	}
	

	
	/**
	 * Carrega os dados do professor logado no sistema.
	 */
	private void carregaDadosDoProfessor() {
		Pessoa pessoa = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);

		if (pessoa != null) {
			this.professor = new ProfessorDAO().buscarProfessor(pessoa.getContato().getEmail(), pessoa.getSenha());
		} else {
			this.professor = new Professor();
		}
	}


	/**
	 * Salva no banco de dados o registro do objeto que está sendo manipulado
	 * por esta classe controladora.
	 */
	public void salvarProfessor(){
		
		converterDatasDoProfessor();
		boolean cadastrou = new ProfessorDAO().salvar(professor);
		
		if(cadastrou){
			Mensagem.ExibeMensagem("Dados atualizados com sucesso!");
		}else{
			Mensagem.ExibeMensagemErro("Não foi possível atualizar os dados.");
		}
	}

	
	
	/**
	 * Atualiza no banco de dados o registro do professor logado no sistema.
	 */
	public void atualizarProfessor() {
		System.out.println("Atualizando professor: " + professor);
		
		converterDatasDoProfessor();
		new ProfessorDAO().atualizar(professor);		
		Mensagem.ExibeMensagem("Registro atualizado com sucesso!");
	}
	
	
	
	
	/**
	 * Converte as datas do perfil do professor para o formato String.
	 */
	private void converterDatasDoProfessor(){		
		
		// Necessário para construir a máscara desejada.
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		/* Se o professor não informar a data de admissão e nascimento, 
		 * cadastra no banco uma String vazia. */
		if(dataAdmissao == null){			
			professor.setDataAdmissao("");
		}else{
			professor.setDataAdmissao(sdf.format(dataAdmissao));
		}
		
		if(dataNascimento == null){
			professor.setDataNascimento("");
		}else{
			professor.setDataNascimento(sdf.format(dataNascimento));
		}
	}

	
	
	public Professor getProfessor() {
		return professor;
	}

	public void setAluno(Professor prof) {
		this.professor = prof;
	}
	
	public List<String> getEstadosBrasileiros() {
		return estadosBrasileiros;
	}
	public List<String> getEstadoCivil() {
		return estadoCivil;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
}
