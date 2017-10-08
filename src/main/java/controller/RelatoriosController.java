package controller;

import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import model.dao.RelatorioDAO;

@ManagedBean
@ViewScoped
public class RelatoriosController {

	// Modelo do gráfico.
	private BarChartModel barModel;
	private HorizontalBarChartModel horizontalBarModel;
    
	
    public RelatoriosController() {
        createBarModel();
        createHorizontalBarModel();
    }
 
    
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
    public BarChartModel getBarModel() {
        return barModel;
    }
 
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        model.setAnimate(true);
        model.setShadow(false);
        
        ChartSeries chart = new ChartSeries();
        chart.setLabel("Sistemas de Informação");
        chart.set("Java", 60);
        chart.set("Banco de Dados", 45);
        chart.set("Android", 10);
 
        model.addSeries(chart);
         
        return model;
    }
     
     
    private void createBarModel() {
        barModel = initBarModel();         
        barModel.setLegendPosition("ne");
    } 
    
    
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
 
        List<Map<String, Object>> lista = new RelatorioDAO().buscaQtdeDeProjetosPorProfessor();
        
        ChartSeries serie = new ChartSeries();
        serie.setLabel("Professores");
        
        int maiorQtde = 0;
        
        for (Map<String, Object> map : lista) {
			String nome = (String) map.get("Nome");
			Integer qtdeProjetos = (Integer) map.get("Qtde");
			serie.set(nome, qtdeProjetos);
			
			if(qtdeProjetos > maiorQtde)
				maiorQtde = qtdeProjetos;
		}

        horizontalBarModel.addSeries(serie);
         
        horizontalBarModel.setTitle("Número de Projetos Cadastrados pelos Professores");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);
        horizontalBarModel.setShadow(false);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setMin(new Integer(0));
        xAxis.setMax(new Integer(maiorQtde+2));
        xAxis.setTickCount((maiorQtde + 1));
    }

}
