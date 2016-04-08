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
import java.util.Timer;
import java.util.TimerTask;

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

    public static List<Status> getTweets(final String q) {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            
            @Override
            public void run() {
 

        long amountOfTweets = 0;
       

        try {

            long maxID = -1;

            Query query = new Query(q);
            //printTimeLine(query);
            Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
            RateLimitStatus searchLimit = rateLimitStatus.get("/search/tweets");
            for (int batchNumber = 0; MAX_QUERIES < 10; batchNumber++) {

                System.out.printf("\n\n!!! batch %d\n\n", batchNumber);

                if (searchLimit.getRemaining() == 0) {
                    // so as to not get blocked by twitter
                    Thread.sleep(searchLimit.getSecondsUntilReset() + 3 * 1001);
                }

                query.setCount(TWEETS_PER_QUERY);// constant value of 100
                query.setResultType(Query.ResultType.recent);
                query.setLang("en");// only english tweets

                if (maxID != -1) {
                    query.setMaxId(maxID - 1);// so the first querys not set to previous max
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
                    storeTweet(s);// where stored in db

                    System.out.printf("At%s , @%-20s said : %s\n", // debugging purposes
                            s.getCreatedAt().toString(),
                            s.getUser().getScreenName(),
                            s.getText());
                    searchLimit = result.getRateLimitStatus();
                    System.out.printf("\n\nA total of %d tweet retrieved\n", amountOfTweets);

                }

            }

        } catch (TwitterException te) {

            System.out.println("Error Code :" + te.getErrorCode());
            System.out.println("Exception Code " + te.getExceptionCode());
            System.out.println("Status Code " + te.getStatusCode());

            if (te.getStatusCode() == 401) {
                System.out.println("Twitter Error :\nAuthentication "
                        + "credentials (https://dev.twitter.com/auth) "
                        + " are either missing of incorrect, "
                        + "\nplease check consumer key /secret");
            }
        } catch (InterruptedException ex) {

        }

       
                      
            }
        };

// schedule the task to run starting now and then every hour...
        timer.schedule(hourlyTask, 0l, 1000 * 60 * 60 );
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
        basicOBJ.put("created_at", tweet.getCreatedAt());

        try {
            items.insert(basicOBJ);

        } catch (Exception e) {
            System.out.println("Could not store Tweet please try again" + e.getMessage());

        }

    }

}
