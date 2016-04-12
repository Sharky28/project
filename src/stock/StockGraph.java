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
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StockGraph extends ApplicationFrame {

    public StockGraph(String FrameTitle, String chartTitle) {
        super(FrameTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Date", "Price",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 367));
        JScrollPane pane = new JScrollPane();
        pane.add(chartPanel);
        setContentPane(chartPanel);

    }
    
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        GregorianCalendar start = new GregorianCalendar(2016, 0, 18);
//        Date start = new Date(2015, 0, 18);
        GregorianCalendar end = new GregorianCalendar(2016, 3, 1);
//        Date end = new Date(2016, 3, 1);

        StockDownloader downloader = new StockDownloader();
        StockManager manager = new StockManager();

        List<Stock> stocks = manager.getStocks(downloader.downloadStocks("AAPL", start, end, "w"));

        String price = "";

        for (Stock s : stocks) {

            dataset.addValue(s.getClose(), price, s.getNumericalDate());

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
        JScrollPane pane = new JScrollPane();
//        pane.add(chart);
//        pane.setVisible(true);
    }
}
