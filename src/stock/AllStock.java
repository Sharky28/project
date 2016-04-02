/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author sharmarke
 */
public class AllStock {

    private List<Stock> stocks;

    public AllStock(String company) {
        stocks = new ArrayList<>();
        loadStocks(company);
    }

    public static void main(String[] args) {
        AllStock as = new AllStock("Apple");
        as.showStocks();
    }

    private void loadStocks(String choice) {
        BufferedReader in;
        String path = "";
        if (choice.equals("Apple")) {
            path = "C:\\stocks\\AppleStocks.csv";
        } else if (choice.equals("Samsung")) {
            path = "C:\\stocks\\SamsungStocks.csv";
        } else if (choice.equals("Nokia")) {
            path = "C:\\stocks\\NokiaStocks.csv";
        }
        try {
            in = new BufferedReader(new FileReader(path));
            String line = "";
            DateFormat df = new SimpleDateFormat("d/M/y");

            String inputLine;
            in.readLine();
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");
                TimeSeries series1 = new TimeSeries("Data");
                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());

                Stock temp = new Stock(date, open, high, low, close, volume, adjClose);
                add(temp);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllStock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AllStock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AllStock.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    public void add(Stock s) {
        stocks.add(s);
    }

    public List<Stock> getStocks() {

        return stocks;
    }

    public void showStocks() {
        for (Stock stock : stocks) {
            System.out.println("Date :" + stock.getDate().toString()
                    + "\nOpen :" + stock.getOpen()
                    + "\nClose :" + stock.getClose()
                    + "\nHigh :" + stock.getHigh()
                    + "\nLow :" + stock.getLow()
                    + "\nVolume :" + stock.getVolume()
                    + "\nAdjusted Close :" + stock.getAdjustedClose()
                    + "\n*******************");
        }
    }
}
