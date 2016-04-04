/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author sharmarke
 */
public class StockDownloader {

    private List<String> stockLines;
    

    public StockDownloader() {
        stockLines = new ArrayList<>();
    }

    public static void main(String[] args) {
//        GregorianCalendar start = new GregorianCalendar(2015, 11, 29);
//        GregorianCalendar end = new GregorianCalendar(2016, 3, 1);
//
//        StockDownloader test = new StockDownloader();
//        test.downloadStocks("AAPL", start, end);

    }

    public List<String> downloadStocks(String symbol, GregorianCalendar start, GregorianCalendar end,String interval) {
        String url2 ="http://real-chart.finance.yahoo.com/table.csv?s="
                + symbol
                + "&d="+ end.get(Calendar.MONTH)
                + "&e="+ end.get(Calendar.DAY_OF_MONTH)
                + "&f="+ end.get(Calendar.YEAR)
                + "&g="+interval
                + "&a="+ start.get(Calendar.MONTH)
                + "&b="+ start.get(Calendar.DAY_OF_MONTH)
                + "&c="+start.get(Calendar.YEAR)
                + "&ignore=.csv";
//
//        String url = "http://real-chart.finance.yahoo.com/table.csv?s=" + symbol
//                + "&a=" + start.get(Calendar.MONTH)
//                + "&b=" + start.get(Calendar.DAY_OF_MONTH)
//                + "&c=" + start.get(Calendar.YEAR)
//                + "&d=" + end.get(Calendar.MONTH)
//                + "&e=" + end.get(Calendar.DAY_OF_MONTH)
//                + "&f=" + end.get(Calendar.YEAR)
//                + "&g=d&ignore=.csv";

        try {
            URL yahooFinance = new URL(url2);
            URLConnection data = yahooFinance.openConnection();
            Scanner in = new Scanner(data.getInputStream());

            if (in.hasNext()) {
                in.nextLine(); // gets rid of first line
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    stockLines.add(line);
                    //System.out.println(line); //for debugging purposes
                }
            }
        } catch (Exception e) {
        }

        return stockLines;
    }

    

}
