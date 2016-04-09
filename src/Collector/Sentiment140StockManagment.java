/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sentiment.SentimentAnalyser;
import stock.Stock;
import stock.StockDownloader;
import stock.StockManager;

/**
 *
 * @author sharmarke
 */
public class Sentiment140StockManagment {

    private stock.StockDownloader downloader;
    private stock.StockManager manager;
    private Sentiment140 tweetsManager;

    public Sentiment140StockManagment() {
      
            downloader = new StockDownloader();
            manager = new StockManager();
            tweetsManager = new Sentiment140();
//        GregorianCalendar start = new GregorianCalendar(2009, 04, 06);
//        GregorianCalendar end = new GregorianCalendar(2009, 05, 06);

 //           buildCsv();
          getSentimentForEachDay();

//        for (Stock downloadedStockObject : downloadedStockObjects) {
//            System.out.println(downloadedStockObject.toString());
//    
        
    }

    public void buildCsv() {
        List<String> downloadedStocksStrings = downloader.downloadNYSE();
        List<stock.Stock> downloadedStockObjects = manager.getStocks(downloadedStocksStrings);
        List<Tweet> tweets = tweetsManager.getTweetObjects();

        List<SentiStockScore> sentiscores = new ArrayList<>();
        for (Stock stock : downloadedStockObjects) {
            List<Double> tempScores = new ArrayList<>();
            Date stockdate = stock.getDate();
            for (Tweet tweet : tweets) {
                if (tweet.getDate().equals(stockdate)) {
                    double score = tweet.getSentiment();
                    tempScores.add(score);
                    double avg = calculateAveragePolarity(tempScores);
                    SentiStockScore csvLine = new SentiStockScore();
                    csvLine.setDate(stockdate);
                    csvLine.setSentimentPolarity(avg);
                    csvLine.setClosingPrice(stock.getClose());
                    sentiscores.add(csvLine);
                }
            }
        }
        System.out.println(sentiscores.size());

        System.out.println(sentiscores);

        //      String[] lines = new String[downloadedStockObjects.size()];
//        for (int i = 0; i < downloadedStockObjects.size(); i++) {
//
//            Stock stock = downloadedStockObjects.get(i);
//
//            for (int j = 0; j < tweets.size(); j++) {
//
//                Tweet tweet = tweets.get(j);
//                if (stock.getDate().equals(tweet.getDate())) {
//
//                    line = stock.getDate().toString() + "," + tweet.getSentiment() + "," + stock.getClose();
//                    System.out.println(line);
//                }
//
//            }
//
//        }
    }

    public void getSentimentForEachDay() {
        List<Tweet> tweets = tweetsManager.getTweetObjects();
        sentiment.SentimentAnalyser sa = new SentimentAnalyser();
        
        for (Tweet tweet : tweets) {
            double score = sa.calculateTweetPolarity(tweet.getTweetTxt());
    //        System.out.println(score);
        }
    }

    public void buildcsv2() {
        List<String> downloadedStocksStrings = downloader.downloadNYSE();
        List<stock.Stock> downloadedStockObjects = manager.getStocks(downloadedStocksStrings);
         List<Tweet> tweets = tweetsManager.getTweetObjects();

        List<Date> stockDates = new ArrayList<>();
        for (Stock stock : downloadedStockObjects) {
            stockDates.add(stock.getDate());
        }

        List<Double> tempScores = new ArrayList<>();
        String line = "";
        for (Tweet tweet : tweets) {
            if (stockDates.contains(tweet.getDate())) {
                double score = tweet.getSentiment();
                tempScores.add(score);
                double avg = calculateAveragePolarity(tempScores);
                System.out.println(tweet.getDate() + "," + avg);

            }
        }

    }

    public double calculateAveragePolarity(List<Double> sc) {
        List<Double> scores = sc;
        double sum = 0.0;
        for (Double score : scores) {
            sum = sum + score;
        }
        double avg = 0.0;
        avg = sum / scores.size();
        return avg;
        //       System.out.println("Average :" + avg);
    }

    public static void main(String[] args) {
        Sentiment140StockManagment sm = new Sentiment140StockManagment();

        //      sm.manager.showStocks();
    }

}
