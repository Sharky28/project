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
    private static List<Status> statuses = new ArrayList<>();

    public TweetAnalyzer() {
        manager = new SentiWordNetManager();
        tweetCollector = new TweetCollector();
        process = new PreProcessor();
        statuses = TweetCollector.printTimeLine("CNN");
    }

    public static void main(String[] args) {
        TweetAnalyzer tn = new TweetAnalyzer();
        for (Status a : statuses) {
            if ("en".equals(a.getLang())) {
                String tweet = PreProcessor.normalizeTweet(a.getText());
                calculateTweetScore(tweet);
            }
        }

    }

    public static void calculateTweetScore(String tw) {

        StringTokenizer tweet = new StringTokenizer(tw);
        double sum = 0;
        double score = 0;
        List<String> words = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        while (tweet.hasMoreElements()) {
            String nextword = (String) tweet.nextElement();
            words.add(nextword);
        }
        for (String word : words) {
            SentiWordNetManager swn = new SentiWordNetManager();
            score = score + swn.calculateWordScore(word);
            scores.add(score);
            sum = sum + score;
        }
        double avg = sum / scores.size();
        System.out.println(tw + avg);

    }

}
