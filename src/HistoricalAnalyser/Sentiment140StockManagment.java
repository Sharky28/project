/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistoricalAnalyser;

import Collector.Tweet;
import HistoricalAnalyser.Sentiment140;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.list;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import sentiment.SentimentAnalyser;
import sentiment.SentimentScore;
import stock.Stock;
import stock.StockDownloader;
import stock.StockManager;

public class Sentiment140StockManagment {

    private stock.StockDownloader downloader;
    private stock.StockManager manager;
    private Sentiment140 tweetsManager;
    CSVReader reader;
    SentimentAnalyser analyser;

    public Sentiment140StockManagment() throws IOException {

        downloader = new StockDownloader();
        manager = new StockManager();
        tweetsManager = new Sentiment140();
        analyser = new SentimentAnalyser();

//        GregorianCalendar start = new GregorianCalendar(2009, 04, 06);
//        GregorianCalendar end = new GregorianCalendar(2009, 05, 06);
        //           buildCsv();
        //         getSentimentForEachDay();
        ////      calculateTweets();
        calculateAverages();
        //       makeStockCsv();
//        for (Stock downloadedStockObject : downloadedStockObjects) {
//            System.out.println(downloadedStockObject.toString());
//    
    }

    public void calculateAverages() {
        List<String> downloadedStocksStrings = downloader.downloadNYSE();
        List<stock.Stock> downloadedStockObjects = manager.getStocks(downloadedStocksStrings);
       
        List<Tweet> tweets = tweetsManager.getTweetObjects();

        List<SentiStockScore> sentiscores = new ArrayList<>();
        for (Stock stock : downloadedStockObjects) {
            List<Double> tempScores = new ArrayList<>();
            Date stockdate = stock.getDate();
            for (Tweet tweet : tweets) {
                if (tweet.getDate().equals(stockdate)) {
                    double score = analyser.calculateTweetPolarity(tweet.getTweetTxt());
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
    }

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
    //    }
    public void calculateTweets() {

        List<Tweet> tweets = tweetsManager.getTweetObjects();
        List<sentiment.SentimentScore> scores = new ArrayList<>();
        SentimentAnalyser analyser = new SentimentAnalyser();

        for (Tweet tweet : tweets) {
            DateTime date = new DateTime(tweet.getDate());
            double score = analyser.calculateTweetPolarity(tweet.getTweetTxt());
            scores.add(new SentimentScore(date, score));
        }

        System.out.println(scores.size());

//        Map<DateTime, List<SentimentScore>> map = new HashMap<DateTime, List<SentimentScore>>();
//        for (SentimentScore item : scores) {
//            List<SentimentScore> list = map.get(item.getTime());
//            if (list == null) {
//                list = new ArrayList<SentimentScore>();
//                map.put(item.getTime(), list);
//            }
//            list.add(item);
//            System.out.println(list.size());
//        }
    }

    public void makeStockCsv() {
        List<String> downloadedStocksStrings = downloader.downloadNYSE();
        List<stock.Stock> downloadedStockObjects = manager.getStocks(downloadedStocksStrings);

        for (Stock downloadedStockObject : downloadedStockObjects) {
            System.out.println(downloadedStockObject.getClose());
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

    public void calculateAverages2() {
        List<Tweet> tweets = tweetsManager.getTweetObjects();
        List<sentiment.SentimentScore> scores = new ArrayList<>();
        SentimentAnalyser analyser = new SentimentAnalyser();

        for (Tweet tweet : tweets) {
            DateTime date = new DateTime(tweet.getDate());
            double score = analyser.calculateTweetPolarity(tweet.getTweetTxt());
            scores.add(new SentimentScore(date, score));
        }

        System.out.println(scores.size());

        List<DateTime> dates = new ArrayList<>();
        Set<DateTime> set = new HashSet<DateTime>(dates);
        for (SentimentScore score : scores) {

            dates.add(score.getTime());
            set.add(score.getTime());
        }

        System.out.println(dates.size());
        System.out.println(set.size());

    }

    public static void main(String[] args) throws IOException {
        Sentiment140StockManagment sm = new Sentiment140StockManagment();

        //      sm.manager.showStocks();
    }

}
