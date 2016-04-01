/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

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

    SentiWordNetManager manager;
    TwitterConnector connector;
    TweetCollector tweetCollector;
    PreProcessor process;
    private static List<Status> statuses;
    private static List<Double> sessionScores;
    private static Lexicons collections;
    private static double neg = 1.0;
    private static int intens = 0;
    private static double[] intensifierarray = new double[20];

    public TweetAnalyzer() {
        manager = new SentiWordNetManager();
        tweetCollector = new TweetCollector();
        process = new PreProcessor();
        statuses = TweetCollector.printTimeLine("CNN");
        sessionScores = new ArrayList<Double>();
        collections = new Lexicons();
    }

    public static void main(String[] args) {
        TweetAnalyzer tn = new TweetAnalyzer();

        for (Status a : statuses) {
            if ("en".equals(a.getLang())) {
                String tweet = PreProcessor.normalizeTweet(a.getText());
                //calculateTweetScore(tweet);
                calculateSentancescore(tweet);

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
    public static double calculateSentancescore(String tweet) {

        double score = 0.0d;

        StringTokenizer tokenizer = new StringTokenizer(tweet);
        while (tokenizer.hasMoreElements()) {
            String nextWord = (String) tokenizer.nextElement();
            if (collections.intensifiers.get(nextWord) != null) {
                //storing each intensifying word in an array to handle cases like "very very good"
                intensifierarray[intens++] = collections.intensifiers.get(nextWord);

            }
            if (collections.getDictionary().get(nextWord) != null) {
                for (int a = 0; a < intens; a++) {
                    //the value of the next words sentiment is multiplied by each intensifier 
                    score = score + (collections.getDictionary().get(nextWord) * intensifierarray[a]);
                    intensifierarray[a] = 0.0; // empty the array so as to not influence later features
                }
                // always multiply by the value of neg , should be 1.0 if no negations found
                score = score + (neg * collections.getDictionary().get(nextWord));
                neg = 1;//ensures it stays one 

            } else if (collections.getNegations().contains(nextWord)) {
                //negation found so make the value of neg a negative 
                neg = (-1.0) * neg;
            }
        }
        //trims the double
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(score));

    }

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
