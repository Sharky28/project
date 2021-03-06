/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import sentiment.SentimentEngine;
import java.text.ParseException;
import java.util.List;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.joda.time.DateTime;
import sentiment.SentimentManager;
import sentiment.SentimentScore;

/**
 *
 * @author sharmarke
 */
public class SentimentGraph extends ApplicationFrame {

    public SentimentGraph(String applicationTitle, String chartTitle) throws ParseException {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Date", "Polarity",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 367));
        JScrollPane pane = new JScrollPane();
        pane.add(chartPanel);
        setContentPane(chartPanel);

    }

    private DefaultCategoryDataset createDataset() throws ParseException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        SentimentEngine se = new SentimentEngine();
        se.saveScores();
        SentimentManager sm = se.getSentimentManager();

//        DateTime someTime = new DateTime();
//        DateTime earliest = someTime.minusMinutes(5);
 //       List<SentimentScore> scores = sm.getScoresForTweetsAfter(earliest);
        List<SentimentScore> scores = sm.getScores();
        String value = "";

        for (SentimentScore s : scores) {

            dataset.addValue(s.getScore(), value, s.getTime());

        }
        return dataset;
    }
     
    public static void main(String[] args) throws ParseException {
        SentimentGraph chart = new SentimentGraph(
                "Sentiment Chart",
                "Sentiment");

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        JScrollPane pane = new JScrollPane();

    }
}
