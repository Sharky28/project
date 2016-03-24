/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.List;
import java.util.Map;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 *
 * @author sharmarke
 */
public class TweetCollector {

    private final TwitterConnector tc;
    private static twitter4j.Twitter twApple;
  

    private static TweetCollector tcc;

    public TweetCollector() {
        tc = new TwitterConnector();
        tc.makeConnection();
        twApple = tc.getTwitter();
    }

    public static void main(String[] args) {
        try {

            

            TweetCollector.tcc = new TweetCollector();

            String query = "Nokia";
            printTimeLine(query);

        } catch (Exception e) {
        }
    }

    public static List<Status> printTimeLine(String account) {

        try {
            Query query = new Query(account);

            QueryResult result = twApple.search(query);

            List<Status> tweets = result.getTweets();

            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
            return tweets;

        } catch (TwitterException te) {
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        return null;
    }

}
