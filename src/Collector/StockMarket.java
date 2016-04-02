/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.util.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JTable;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author sharmarke
 */
public class StockMarket {

    private static final int DATE = 0;
    private static final int OPEN = 1;
    private static final int HIGH = 2;
    private static final int LOW = 3;
    private static final int CLOSE = 4;
    private static final int VOLUME = 5;
    private static final int ADJCLOSE = 6;

    private List<Date> dates;
    private static List<Double> opens;
    private List<Double> closes;
    private List<Double> highs;
    private List<Double> lows;
    private List<Double> volumes;
    private List<Double> adjcloses;

    public StockMarket() {
        dates = new ArrayList<>();
        opens = new ArrayList<>();
        closes = new ArrayList<>();
        highs = new ArrayList<>();
        lows = new ArrayList<>();
        volumes = new ArrayList<>();
        adjcloses = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {

        StockMarket m = new StockMarket();
        BufferedReader reader = null;
        Calendar cal = new GregorianCalendar();

        try {
            reader = new BufferedReader(new FileReader("C:\\stocks\\AppleStocks.csv"));
            String line = "";

            if (reader.readLine() != null) {
                reader.readLine();
            }
            while (reader.readLine() != null) {
                line = reader.readLine();

                StringTokenizer tokenizer = new StringTokenizer(line);
                String dateval = tokenizer.nextToken();
                Date date = new Date(dateval);
                m.dates.add(date);
                m.opens.add((Double) tokenizer.nextElement());
                m.closes.add((Double) tokenizer.nextElement());
                m.highs.add((Double) tokenizer.nextElement());
                m.lows.add((Double) tokenizer.nextElement());
                m.volumes.add((Double) tokenizer.nextElement());
                m.adjcloses.add((Double) tokenizer.nextElement());

            }
        } catch (Exception e) {
        } finally {
            reader.close();
        }

        printOpens();

    }

    protected OHLCDataItem[] getData(String stockSymbol) {
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("C:\\stocks\\AppleStocks.csv"));
            String line = "";
            DateFormat df = new SimpleDateFormat("y-M-d");

            String inputLine;
            in.readLine();
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());

                OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
                dataItems.add(item);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
        Collections.reverse(dataItems);

        //Convert the list into an array
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

        return data;
    }

    public static void printOpens() {
        List<Double> temp = getOpens();
        for (Double temp1 : temp) {
            System.out.println(temp1.toString());
        }

    }

    public static ArrayList<Double> getOpens() {
        return (ArrayList<Double>) opens;
    }

    //      ReadFiles r = new ReadFiles();
//        System.out.println(r.checkIsFile());
//        System.err.println("" + r.findRowNumber());
//        r.convertToArray();
//        r.printArray();
//        Formulas f = new Formulas();
//
//        f.createDataArray(r.getArray());
//    
    public class CandlestickDemo2 extends JFrame {

        public CandlestickDemo2(String stockSymbol) {
            super("CandlestickDemo");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DateAxis domainAxis = new DateAxis("Date");
            NumberAxis rangeAxis = new NumberAxis("Price");
            CandlestickRenderer renderer = new CandlestickRenderer();
            XYDataset dataset = getDataSet(stockSymbol);

            XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

            //Do some setting up, see the API Doc
            renderer.setSeriesPaint(0, Color.BLACK);
            renderer.setDrawVolume(false);
            rangeAxis.setAutoRangeIncludesZero(false);
            domainAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

            //Now create the chart and chart panel
            JFreeChart chart = new JFreeChart(stockSymbol, null, mainPlot, false);
            ChartPanel chartPanel = new ChartPanel(chart, false);
            chartPanel.setPreferredSize(new Dimension(600, 300));

            this.add(chartPanel);
            this.pack();
        }

        protected AbstractXYDataset getDataSet(String stockSymbol) {
            //This is the dataset we are going to create
            DefaultOHLCDataset result = null;
            //This is the data needed for the dataset
            OHLCDataItem[] data;

            //This is where we go get the data, replace with your own data source
            data = getData(stockSymbol);

            //Create a dataset, an Open, High, Low, Close dataset
            result = new DefaultOHLCDataset(stockSymbol, data);

            return result;
        }

        //This method uses yahoo finance to get the OHLC data
    }

}
