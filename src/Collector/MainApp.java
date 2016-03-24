/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import twitter4j.Status;

/**
 *
 * @author sharmarke
 */
public class MainApp {

    ///TwitterConnector connector;
    TweetCollector tweetCollector;
    PreProcessor process;
    private static MainApp app;

    private static List<Status> statuses;

    public MainApp() {
        tweetCollector = new TweetCollector();
        
        statuses = tweetCollector.printTimeLine("CNN");
       
        for (Status statuse : statuses) {
            String tweet =PreProcessor.normalizeTweet(statuse.getText());
            System.out.println(tweet);
        }
        System.out.println(">>>>>>>>");
        
       

    }

    public static void main(String[] args) {
        MongoDbConnector.initMongoDB();
        app = new MainApp();
//        process();
//        process2();
//        process3();
        MongoManager manager = new MongoManager();
        List<Status> tweets = statuses;
        MongoManager.storeTweets(tweets);

    }

    public static void process() {
        String tweet = " @yerdekigokyuzu1 - RT @denizatam: Cnn İnt'in Türkiye'yi dünya haritasından silmesi https://t.co/h5DJwEuXRo";
        String newtweet = PreProcessor.normalizeTweet(tweet);
        System.out.println(tweet + "\n" + newtweet);
    }

    public static void process2() {
        String tweet = " a bb ccc dddd eeee";
        String newTweet = PreProcessor.removeDuplicates(tweet);
        System.out.println(tweet + "\n" + newTweet);
    }

    public static void process3() {
        String tweet = " sfdfsf sdffsdf #wow";
        String newTweet = PreProcessor.normalizeTweet(tweet);
        System.out.println(tweet + "\n" + newTweet);
    }
}
