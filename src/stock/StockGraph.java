/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

/**
 *
 * @author sharmarke
 */
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class StockGraph extends ApplicationFrame {

    public StockGraph(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Date", "Price",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        AllStock aS = new AllStock("Apple");
        List<Stock> stocks = aS.getStocks();
        String price = "";

        for (Stock s : stocks) {

            dataset.addValue(s.getClose(), price, s.getDate().toString());

        }
        return dataset;
    }

    public static void main(String[] args) {
        StockGraph chart = new StockGraph(
                "Price Chart",
                "Historical prices for :Apple");

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
