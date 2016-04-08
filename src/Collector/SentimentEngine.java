/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import sentiment.SentimentAnalyser;
import sentiment.PreProcessor;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import sentiment.SentimentManager;
import sentiment.SentimentScore;

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
    private SentimentManager sM;

    public SentimentEngine() throws ParseException {
        connection = new MongoDbConnector();
        database = connection.getDB();
        items = database.getCollection("tweetcoll");
        tweets = new ArrayList<>();
        analyzer = new SentimentAnalyser();
        sessionScores = new ArrayList<>();
        sM = new SentimentManager();
//        loadTweets();
//        printSentiment();
//        System.out.println(items.count());
//        calculateAveragePolarity(sessionScores);
        //       printDates();
 //       saveScores();
 //       sM.printScores();
        //       printTweets();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

//        Date start = new java.util.Date(2016-1900, 3, 1);
//        Date end = new java.util.Date(2016-1900, 03, 7);
        String date = "2016.04.01";
        String date1 = "2016.04.07";
        Date start = simpleDateFormat.parse(date);
        Date end = new Date();

 //       getTweetsBetween(start, end);
        //      getTweetsBetween(start, end);
        System.out.println(items.count());

    }

    public static void main(String[] args) throws ParseException {
        SentimentEngine se = new SentimentEngine();
    }

    public void loadTweets() {
        DBCollection collection = database.getCollection("tweetcoll");
        for (DBObject bson : collection.find()) {
            if (bson != null) {
                String tweet = (String) bson.get("tweet_text");

                tweets.add(tweet);
            }

        }

    }

    public void saveScores() {

        for (DBObject bson : items.find()) {
            if (bson != null) {
                //get the tweet
                String tweet = (String) bson.get("tweet_text");
                tweets.add(tweet);
                double score = analyzer.calculateTweetPolarity(tweet);
                // get the time
                BasicDBObject bTweet = (BasicDBObject) bson;
                ObjectId objectId = bTweet.getObjectId("_id");
                long millis = objectId.getTime();
                DateTime date = new DateTime(millis);
                
                
                //create the sentimetn score and add
                sentiment.SentimentScore singleScore = new SentimentScore(date, score);
                sM.addScore(singleScore);

            }

        }

    }

    public void printDates() {
        DBCollection collection = database.getCollection("tweetcoll");

        List<DBObject> temps = collection.find().toArray();
        for (DBObject temp : temps) {
            BasicDBObject tweet = (BasicDBObject) temp;
            ObjectId objectId = tweet.getObjectId("_id");
            long millis = objectId.getTime();
            DateTime date = new DateTime(millis);

            System.out.println(" created on " + date);
        }
    }

    public void printSentiment() {
        for (String tweet : tweets) {
            //           sentiment.SentimentScore= new sentiment.SentimentScore(tweet.g, s)
            double score = analyzer.calculateTweetPolarity(tweet);
            sessionScores.add(score);
            System.out.println(PreProcessor.normalizeTweet(tweet) + ", " + score);
        }
    }

    private void getTweetsBetween(Date start, Date end) {
        //      items.find({"created_at" : { $gte : new Date("2016-04-01") }}); mongo terminal

        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("created_at", new BasicDBObject("$gt", start).append("$lt", end));
        DBCursor cursor = items.find(getQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void printTweets() {
//
//        for (String tweet : tweets) {
//            System.out.println(tweet + "\n");
//        }
    }
    
    public  SentimentManager getSentimentManager()
    {
        return this.sM;
    }

    public void calculateAveragePolarity(List<Double> sc) {
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
