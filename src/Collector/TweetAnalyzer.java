/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import twitter4j.Status;

/**
 *
 * @author sharmarke
 */
public class TweetAnalyzer {

    SentiWordNetManager manager;
    TwitterConnector connector;
    TweetCollector tweetCollector;
    PreProcessor process;
    private static List<Status> statuses ;
    List<Double> sessionScores ;

    public TweetAnalyzer() {
        manager = new SentiWordNetManager();
        tweetCollector = new TweetCollector();
        process = new PreProcessor();
        statuses = TweetCollector.printTimeLine("CNN");
        sessionScores = new ArrayList<Double>();
    }

    public static void main(String[] args) {
        TweetAnalyzer tn = new TweetAnalyzer();
        
        for (Status a : statuses) {
            if ("en".equals(a.getLang())) {
                String tweet = PreProcessor.normalizeTweet(a.getText());
                //calculateTweetScore(tweet);
                calculateTweetpolarity(tweet); 
            }
        }

    }

//    public static void calculateTweetScore(String tw) {
//
//        StringTokenizer tweet = new StringTokenizer(tw);
//        double sum = 0;
//        double score = 0;
//        List<String> words = new ArrayList<>();
//        List<Double> scores = new ArrayList<>();
//
//        while (tweet.hasMoreElements()) {
//            String nextword = (String) tweet.nextElement();
//            words.add(nextword);
//        }
//
//        for (String word : words) {
//            SentiWordNetManager swn = new SentiWordNetManager();
//            score = score + swn.calculateWordScore(word);
//            scores.add(score);
//            sum = sum + score;
//        }
//        double avg = sum / scores.size();
//        System.out.println(tw + avg);
//
//    }

    public static void calculateTweetpolarity(String tw) {
        StringTokenizer tweet = new StringTokenizer(tw);
        List<String> words = new ArrayList<>();
        
        SentiWordNetManager swn = new SentiWordNetManager();
        
         while (tweet.hasMoreElements()) {
            String nextword = (String) tweet.nextElement();
            words.add(nextword);
        }
        
        double score = swn.calculateSentancescore(words);
        
        System.out.println(tw+","+score);
       
    }
    
    public static void calculateAveragePolarity(List<Double> sc)
    {
        List<Double> scores = new ArrayList<>();
        double sum =0.0;
        for (Double score : scores) {
            sum = sum + score;
        }
        double avg =0.0;
        avg = sum / scores.size();
        System.out.println("Average :"+ sum);
    }

}
