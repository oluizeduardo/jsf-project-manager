package controller;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import model.dao.RelatorioDAO;
import model.pojo.Pessoa;
import web.SessionUtil;

@ManagedBean
@ViewScoped
public class RelatoriosController {

	// Modelo do gráfico vertical.
	private BarChartModel barModel;
	// Modelo do gráfico horizontal.
	private HorizontalBarChartModel horizontalBarModel;
	// Objeto de acesso aos dados.
	private RelatorioDAO relatorioDAO = new RelatorioDAO();
    // Curso selecionado para consultar as habilidades mais comuns.
	private String cursoSelecionado = null;
	
	
	
    public RelatoriosController() {
    	
    	this.cursoSelecionado = getCursoDoProfessorLogado();
    	
        createVerticalBarModel();
        createHorizontalBarModel();
    }

    
    
    /**
     * Retorna o nome do curso do professor logado no sistema.
     */
    private String getCursoDoProfessorLogado() {
    	Pessoa profLogado = (Pessoa) SessionUtil.getParam(SessionUtil.KEY_SESSION);
    	return profLogado.getCurso().getNome();
	}



	/**
     * Cria um gráfico de barras verticais mostrando quais são as habilidades
     * mais comuns de um determinado curso.
     */
    public void createVerticalBarModel(){
        barModel = new BarChartModel();
                
        List<Map<String, Object>> lista = relatorioDAO.buscaHabilidadesMaisComuns(cursoSelecionado);

        ChartSeries chart = new ChartSeries();
        chart.setLabel(cursoSelecionado);
        int maiorQtde=0;
        
        
        if(lista.isEmpty()){
        	chart.set("Nenhuma habilidade localizada para "+cursoSelecionado, 0);
        }else{
        	// Coloca até 5 habilidades no gráfico.
        	for (int i=0; i < lista.size() && i < 5; i++) {
        		Map<String, Object> map = lista.get( i );
    			String habilidade = (String) map.get("Habilidade");
    			Integer qtdeProjetos = (Integer) map.get("Qtde");
    			chart.set(habilidade, qtdeProjetos);
    			
    			if(qtdeProjetos > maiorQtde)
    				maiorQtde = qtdeProjetos;
    		}
        }
        
        barModel.setShowPointLabels(true);
        barModel.addSeries(chart);
        barModel.setAnimate(true);
        barModel.setShadow(false);
        barModel.setLegendPosition("ne");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(new Integer(0));
        yAxis.setMax(new Integer(maiorQtde+2));
        yAxis.setTickCount((maiorQtde + 1));       
    }

    
    
    /**
     * Cria um gráfico de barras horizontais mostrando os professores
     * que mais cadastraram projetos no sistema.
     */
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
        List<Map<String, Object>> lista = relatorioDAO.buscaQtdeDeProjetosPorProfessor();
        
        ChartSeries serie = new ChartSeries();
        serie.setLabel("Professores");
        
        int maiorQtde = 0;
        
        if(lista.isEmpty()){
        	serie.set("Ninguém cadastrou ainda", 0);
        }else{
        	for (int i=0; i < lista.size() && i < 10; i++) {
        		Map<String, Object> map = lista.get( i );
    			String nome = (String) map.get("Nome");
    			Integer qtdeProjetos = (Integer) map.get("Qtde");
    			serie.set(nome, qtdeProjetos);
    			
    			if(qtdeProjetos > maiorQtde)
    				maiorQtde = qtdeProjetos;
    		}
        }        

        horizontalBarModel.addSeries(serie);         
        horizontalBarModel.setTitle("Número de Projetos Cadastrados pelos Professores");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);
        horizontalBarModel.setShadow(false);
        horizontalBarModel.setShowPointLabels(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setMin(new Integer(0));
        xAxis.setMax(new Integer(maiorQtde+2));
        xAxis.setTickCount((maiorQtde + 1));
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

}