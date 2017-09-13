package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

@ManagedBean
@ViewScoped
public class RelatoriosController {

	
	private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
 
    
    public RelatoriosController() {
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
 
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Sistemas de Informação");
        boys.set("Java", 60);
        boys.set("Banco de Dados", 45);
        boys.set("Android", 10);
 
        model.addSeries(boys);
         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();  
    }
     
    private void createBarModel() {
        barModel = initBarModel();         
        barModel.setLegendPosition("ne");
    } 
    
}
