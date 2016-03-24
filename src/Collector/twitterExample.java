/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.lang.reflect.Method;
import java.util.Map;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 *
 * @author sharmarke
 */
public class twitterExample {

    private static final int TWEETS_PER_QUERY = 100;
    private static final int MAX_QUERIES = 5;

    private final TwitterConnector tc;
    private static twitter4j.Twitter twitter;

    private static TweetCollector tcc;

    public twitterExample() {
        tc = new TwitterConnector();
        tc.makeConnection();
        twitter = tc.getTwitter();

    }

    public static void main(String[] args) {
        long amountOfTweets = 0;
       Class cl = tcc.getClass();
       

        twitterExample te = new twitterExample();
        try {

            long maxID = -1;
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
                Query q = new Query("CNN");
                q.setCount(TWEETS_PER_QUERY);
                q.setResultType(Query.ResultType.recent);
                q.setLang("en");

                if (maxID != -1) {
                    q.setMaxId(maxID - 1);
                }
                QueryResult result = twitter.search(q);
                if (result.getTweets().size() == 0) {
                    break;
                }

                for (Status s : result.getTweets()) {
                    amountOfTweets++;
                    if (maxID == -1 || s.getId() < maxID) {
                        maxID = s.getId();
                    }
                    System.out.printf("At%s , @%-20s said : %s\n",
                            s.getCreatedAt().toString(),
                            s.getUser().getScreenName(),
                            s.getText());
                    searchTweetLimit = result.getRateLimitStatus();
                    System.out.printf("\n\nA total of %d tweet retrieved\n", amountOfTweets);

                }

            }

        } catch (Exception e) {
        }

        
    }

}
