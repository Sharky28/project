/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.util.List;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 *
 * @author sharmarke
 */
public class MongoManager {

    private static MongoDbConnector connection;
    private static MongoManager manager;
    private static DBCollection items;

    public MongoManager() {
        connection = new MongoDbConnector();
        DB db = MongoDbConnector.getDB();
        items = db.getCollection("tweetcoll");

    }

    public static void main(String[] args) {
        manager = new MongoManager();
        MongoDbConnector.initMongoDB();

    }

    public static void storeTweets(List<Status> tweets) {

        for (Status tweet : tweets) {
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
                System.out.println("Storing was a success");
            } catch (Exception e) {
                System.out.println("Could not store Tweet please try again" + e.getMessage());
                
            }

        }
    
    }
}
