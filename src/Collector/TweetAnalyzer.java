/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import sentiment.Lexicons;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.StringTokenizer;
import twitter4j.Status;

/**
 *
 * @author sharmarke
 */
public class TweetAnalyzer {

    TwitterConnector connector;
    TweetCollector tweetCollector;
    PreProcessor process;

    private static List<Status> statuses;
    private static List<Double> sessionScores;

    private static Lexicons lex;

    public TweetAnalyzer() {

        tweetCollector = new TweetCollector();
        process = new PreProcessor();
        // need maybe another class/method  to retrive a given amount of tweets,
        //cant have one method collecting /storing/returning
        statuses = TweetCollector.printTimeLine("CNN");
        sessionScores = new ArrayList<Double>();
        lex = new Lexicons();

    }

    public static void main(String[] args) {
        TweetAnalyzer tn = new TweetAnalyzer();

        for (Status a : statuses) {
            if ("en".equals(a.getLang())) {
                String tweet = PreProcessor.normalizeTweet(a.getText());
                //calculateTweetScore(tweet);
  //              System.out.println(tweet + tn.calculateTweetPolarity(tweet));

            }
        }
        calculateAveragePolarity(sessionScores);

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
//    public static double calculateTweetPolarity(String tweet) {
//
//        double score = 0.0d;
//        double neg = 1.0;
//        int intens = 0;
//        double[] intensifierarray = new double[20];
//
//        StringTokenizer tokenizer = new StringTokenizer(tweet);
//        while (tokenizer.hasMoreElements()) {
//            String nextWord = (String) tokenizer.nextElement();
//            if (lex.intensifiers.get(nextWord) != null) {
//                //storing each intensifying word in an array to handle cases like "very very good"
//                intensifierarray[intens++] = lex.intensifiers.get(nextWord);
//
//            }
//            if (lex.getDictionary().get(nextWord) != null) {
//                for (int a = 0; a < intens; a++) {
//                    //the value of the next words sentiment is multiplied by each intensifier
//                    score = score + (lex.getDictionary().get(nextWord) * intensifierarray[a]);
//                    intensifierarray[a] = 0.0; // empty the array so as to not influence later features
//                }
//                // always multiply by the value of neg , should be 1.0 if no negations found
//                score = score + (neg * lex.getDictionary().get(nextWord));
//                neg = 1;//ensures it stays one
//
//            } else if (lex.getNegations().contains(nextWord)) {
//                //negation found so make the value of neg a negative
//                neg = (-1.0) * neg;
//            }
//        }
//        // collects all scores in order to calculate average
//        sessionScores.add(score);
//        //trims the double
//        DecimalFormat df = new DecimalFormat("#.###");
//        return Double.valueOf(df.format(score));
//
//    }

//    public static void calculateTweetpolarity(String tw) {
//        StringTokenizer tweet = new StringTokenizer(tw);
//        List<String> words = new ArrayList<>();
//
//        SentiWordNetManager swn = new SentiWordNetManager();
//
//        while (tweet.hasMoreElements()) {
//            String nextword = (String) tweet.nextElement();
//            words.add(nextword);
//        }
//
//        double score = swn.calculateSentancescore(tw);
//        sessionScores.add(score);
//        System.out.println(tw + "," + score);
//    }
//
    public static void calculateAveragePolarity(List<Double> sc) {
        List<Double> scores = sc;
        double sum = 0.0;
        for (Double score : scores) {
            sum = sum + score;
        }
        double avg = 0.0;
        avg = sum / scores.size();
        System.out.println("Average :" + sum);
    }

}
