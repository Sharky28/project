/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import sentiment.SentimentAnalyser;
import sentiment.PreProcessor;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharmarke
 */
public class SentimentEngine {

    private MongoDbConnector connection;
    private DB database;
    private DBCollection items;
    private List<String> tweets;
    private SentimentAnalyser analyzer;
    private List<Double> sessionScores;

    public SentimentEngine() {
        connection = new MongoDbConnector();
        database = connection.getDB();
        items = database.getCollection("tweetcoll");
        tweets = new ArrayList<>();
        analyzer = new SentimentAnalyser();
        sessionScores= new ArrayList<>();
        loadTweets();
        printSentiment();
        System.out.println(items.count());
        calculateAveragePolarity(sessionScores);
        //       printTweets();

    }

    public static void main(String[] args) {
        SentimentEngine se = new SentimentEngine();
    }

    public void loadTweets() {
        DBCollection collec1 = database.getCollection("tweetcoll");
        for (DBObject dock : collec1.find()) {
            if (dock != null) {
                String tweet = (String) dock.get("tweet_text");
                tweets.add(tweet);
            }

        }

    }
    

    public void printSentiment() {
        for (String tweet : tweets) {
            double score = analyzer.calculateTweetPolarity(tweet);
            sessionScores.add(score);
            System.out.println(PreProcessor.normalizeTweet(tweet) + ", " + score);
        }
    }

    public void printTweets() {
//
//        for (String tweet : tweets) {
//            System.out.println(tweet + "\n");
//        }
    }

    public  void calculateAveragePolarity(List<Double> sc) {
        List<Double> scores = sc;
        double sum = 0.0;
        for (Double score : scores) {
            sum = sum + score;
        }
        double avg = 0.0;
        avg = sum / scores.size();
        System.out.println("Average :" + avg);
    }

}
