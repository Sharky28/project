/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;

/**
 *
 * @author sharmarke
 */
public class TweetCollector {

    private static final int TWEETS_PER_QUERY = 100;
    private static final int MAX_QUERIES = 5;

    private final TwitterConnector tc;
    private static twitter4j.Twitter twitter;

    private static MongoDbConnector connection;
    // put mongo stuff in dbconnection class
    private static DBCollection items;
    private static List<Status> statuses;

    public TweetCollector() {
        tc = new TwitterConnector();
        tc.makeConnection();
        twitter = tc.getTwitter();
        connection = new MongoDbConnector();
        DB db = connection.getDB();
        items = db.getCollection("tweetcoll");
        statuses = new ArrayList<>();

    }

    public static void main(String[] args) {

        TweetCollector tcc = new TweetCollector();
        statuses = getTweets("Apple");

    }

    public static List<Status> getTweets(String q) {

        long amountOfTweets = 0;

        try {

            long maxID = -1;

            Query query = new Query(q);
            //printTimeLine(query);

            Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
            RateLimitStatus searchTweetLimit = rateLimitStatus.get("/search/tweets");
            System.out.printf("You have %d calls remaining out of %d ,Limit resets in %d,",
                    searchTweetLimit.getRemaining(),
                    searchTweetLimit.getLimit(),
                    searchTweetLimit.getSecondsUntilReset());

            for (int queryNumer = 0; MAX_QUERIES < 10; queryNumer++) {

                System.out.printf("\n\n!!! Starting loop %d\n\n", queryNumer);

                if (searchTweetLimit.getRemaining() == 0) {
                    System.out.printf("Sleeping for%d seconds due rate limit\n", searchTweetLimit.getSecondsUntilReset());
                    Thread.sleep(searchTweetLimit.getSecondsUntilReset() + 2 * 1001);
                }

                query.setCount(TWEETS_PER_QUERY);
                query.setResultType(Query.ResultType.recent);
                query.setLang("en");

                if (maxID != -1) {
                    query.setMaxId(maxID - 1);
                }
                QueryResult result = twitter.search(query);
                if (result.getTweets().size() == 0) {
                    break;
                }

                for (Status s : result.getTweets()) {
                    amountOfTweets++;
                    if (maxID == -1 || s.getId() < maxID) {
                        maxID = s.getId();
                    }
                    storeTweet(s);

                    System.out.printf("At%s , @%-20s said : %s\n",
                            s.getCreatedAt().toString(),
                            s.getUser().getScreenName(),
                            s.getText());
                    searchTweetLimit = result.getRateLimitStatus();
                    System.out.printf("\n\nA total of %d tweet retrieved\n", amountOfTweets);

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return statuses;
    }

    public static List<Status> printTimeLine(String account) {

        try {
            Query query = new Query(account);

            QueryResult result = twitter.search(query);

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

    public static void storeTweet(Status tweet) {

        BasicDBObject basicOBJ = new BasicDBObject();
        basicOBJ.put("user_name", tweet.getUser().getScreenName());
        basicOBJ.put("retweet_count", tweet.getRetweetCount());
        basicOBJ.put("tweet_followers_count", tweet.getUser().getFollowersCount());
        UserMentionEntity[] mentioned = tweet.getUserMentionEntities();
        basicOBJ.put("tweet_mentioned_count", mentioned.length);
        basicOBJ.put("tweet_ID", tweet.getId());
        basicOBJ.put("tweet_text", tweet.getText());

        try {
            items.insert(basicOBJ);

        } catch (Exception e) {
            System.out.println("Could not store Tweet please try again" + e.getMessage());

        }

    }

}


