package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

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
 
        ChartSeries serie = new ChartSeries();
        serie.setLabel("Professores");
        serie.set("Thais Almeida", 10);
        serie.set("Roberto Dias", 15);
        serie.set("Rony Gomes", 22);
        serie.set("André Oliveira", 30);
        serie.set("João Paulo Silva", 50);
        
 
        horizontalBarModel.addSeries(serie);
         
        horizontalBarModel.setTitle("Número de Projetos Cadastrados pelos Professores");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setMin(new Integer(0));
        xAxis.setMax(new Integer(60));
    }

}
