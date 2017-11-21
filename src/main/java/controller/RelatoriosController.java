package controller;

import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;

import model.dao.RelatorioDAO;
import model.pojo.Pessoa;
import model.pojo.ProjetosPublicadosPorCurso;
import web.SessionUtil;

@ManagedBean
@ViewScoped
public class RelatoriosController {

	// Modelo do gr�fico vertical.
	private BarChartModel barModel;
	// Modelo do gr�fico horizontal.
	private HorizontalBarChartModel horizontalBarModel;
	// Modelo de gr�fico de linhas.
	private LineChartModel lineModel;
	// Objeto de acesso aos dados.
	private RelatorioDAO relatorioDAO = new RelatorioDAO();
    // Curso selecionado para consultar as habilidades mais comuns.
	private String cursoSelecionado = null;
	// Curso selecionado para comparar o n�mero de projetos cadastrados.
	private String cursoParaComparar = "Administra��o";
	
	
	
    public RelatoriosController() {
    	
    	this.cursoSelecionado = getCursoDoProfessorLogado();
    	
        createVerticalBarModel();
        createHorizontalBarModel();
        createLineModel();
    }

    
    
    /**
     * Retorna o nome do curso do professor logado no sistema.
     */
    private String getCursoDoProfessorLogado() {
    	Pessoa profLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
    	return profLogado.getCurso().getNome();
	}



	/**
     * Cria um gr�fico de barras verticais mostrando quais s�o as habilidades
     * mais comuns de um determinado curso.
     */
    public void createVerticalBarModel(){
        barModel = new BarChartModel();
        barModel.setTitle("Habilidades Mais Comuns Por Curso");
        
        List<Map<String, Object>> lista = relatorioDAO.buscaHabilidadesMaisComuns(cursoSelecionado);

        ChartSeries chart = new ChartSeries();
        chart.setLabel(cursoSelecionado);
        int maiorQtde=0;   
        
        if(lista.isEmpty()){
        	chart.set("Nenhuma habilidade localizada para "+cursoSelecionado, 0);
        }else{
        	// Coloca at� 5 habilidades no gr�fico.
        	for (int i=0; i < lista.size() && i < 3; i++) {
        		Map<String, Object> map = lista.get( i );
    			String habilidade = (String) map.get("Habilidade");
    			Integer qtdeProjetos = (Integer) map.get("Qtde");
    			chart.set(habilidade, qtdeProjetos);
    			
    			if(qtdeProjetos > maiorQtde)
    				maiorQtde = qtdeProjetos;
    		}
//        	chart.set("Java", 25);
//        	chart.set("HTML", 20);
//        	chart.set("CSS", 17);
//        	chart.set("SQL", 10);
        }
                
        barModel.setShowPointLabels(true);
        barModel.addSeries(chart);
        barModel.setAnimate(true);
        barModel.setShadow(false);
        barModel.setLegendPosition("ne");
        
        for(;(maiorQtde+2)%2!=0;maiorQtde++);
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(new Integer(0));
        yAxis.setMax(new Integer(maiorQtde+2));
        yAxis.setTickCount(3);       
    }

    
    
    /**
     * Cria um gr�fico de barras horizontais mostrando os professores
     * que mais cadastraram projetos no sistema.
     */
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
        List<Map<String, Object>> lista = relatorioDAO.buscaQtdeDeProjetosPorProfessor();
        
        ChartSeries serie = new ChartSeries();
        serie.setLabel("Professores");
        
        int maiorQtde = 0;
        
        if(lista.isEmpty()){
        	serie.set("Ningu�m cadastrou ainda", 0);
        }else{
        	for (int i=0; i < lista.size() && i < 8; i++) {
        		Map<String, Object> map = lista.get( i );
    			String nome = (String) map.get("Nome");
    			Integer qtdeProjetos = (Integer) map.get("Qtde");
    			serie.set(nome, qtdeProjetos);
    			
    			if(qtdeProjetos > maiorQtde)
    				maiorQtde = qtdeProjetos;
    		}
//        	serie.set("Andr� Novaes", 5);
//        	serie.set("Carlos Augusto", 8);
//        	serie.set("Ana Cl�udia", 17);
//        	serie.set("Fabiano Gon�alves", 22);
//        	serie.set("Roberto Rocha", 25);
        }                           
        
        horizontalBarModel.addSeries(serie);         
        horizontalBarModel.setTitle("N�mero de Projetos Por Professor");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);
        horizontalBarModel.setShadow(false);
        horizontalBarModel.setShowPointLabels(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setMin(new Integer(0));
        xAxis.setMax(new Integer(maiorQtde+2));
//        xAxis.setMax(30);
        xAxis.setTickCount(3);
    }
    
    
    
    /**
     * Cria um gr�fico de linhas.
     */
    public void createLineModel(){
    	
    	lineModel = new LineChartModel();
    	lineModel.setTitle("Projetos Destinados Durante o Ano");
        lineModel.setLegendPosition("ne");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Meses"));
        lineModel.setAnimate(true);

        //--- Linha 1 ----
        ChartSeries serie1 = new ChartSeries();
        serie1.setLabel(getCursoDoProfessorLogado());
        
        ProjetosPublicadosPorCurso[] lista1 = relatorioDAO.getProjetosPublicadosDuranteAno(getCursoDoProfessorLogado());     
        for(int i=0; i < lista1.length; i++){
        	serie1.set(getNomeMes(i), lista1[i].getQuantidade());
        }
//        serie1.set("Jan", 2);
//        serie1.set("Fev", 2);
//        serie1.set("Mar", 1);
//        serie1.set("Abr", 1);
//        serie1.set("Mai", 3);
//        serie1.set("Jun", 4);
//        serie1.set("Jul", 0);
//        serie1.set("Ago", 6);
//        serie1.set("Set", 8);
//        serie1.set("Out", 6);
//        serie1.set("Nov", 2);
//        serie1.set("Dez", 0);
        
        //--- Linha 2 ----
        ChartSeries serie2 = new ChartSeries();
        serie2.setLabel(cursoParaComparar);
        
        ProjetosPublicadosPorCurso[] lista2 = relatorioDAO.getProjetosPublicadosDuranteAno(cursoParaComparar);     
        for(int i=0; i < lista2.length; i++){
        	serie2.set(getNomeMes(i), lista2[i].getQuantidade());
        }
//        serie2.set("Jan", 0);
//        serie2.set("Fev", 0);
//        serie2.set("Mar", 5);
//        serie2.set("Abr", 3);
//        serie2.set("Mai", 3);
//        serie2.set("Jun", 4);
//        serie2.set("Jul", 4);
//        serie2.set("Ago", 5);
//        serie2.set("Set", 10);
//        serie2.set("Out", 10);
//        serie2.set("Nov", 5);
//        serie2.set("Dez", 0);
 
        // Adiciona as duas linhas no gr�fico.
        lineModel.addSeries(serie1);
        lineModel.addSeries(serie2);
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Qtde");
        yAxis.setMin(0);
        yAxis.setMax(20);
        yAxis.setTickCount(3);
    }
    
    
    
    /**
     * @param mes O n�mero do m�s que se deseja saber o nome.
     * @return O nome do m�s.
     */
    private String getNomeMes(int mes){
    	String[] mesesDoAno = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};    	
    	return mesesDoAno[mes];
    }
    
    
    
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
    public BarChartModel getBarModel() {
        return barModel;
    }
	public String getCursoSelecionado() {
		return cursoSelecionado;
	}
	public void setCursoSelecionado(String cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}
	public LineChartModel getLineModel() {
		return lineModel;
	}
	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	public String getCursoParaComparar() {
		return cursoParaComparar;
	}
	public void setCursoParaComparar(String cursoParaComparar) {
		this.cursoParaComparar = cursoParaComparar;
	}
	
}