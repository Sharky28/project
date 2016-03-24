/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;


import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;

/**
 *
 * @author sharmarke
 */
public class MongoDbConnector {

    private TweetCollector collector;

    private static DB db;

    public MongoDbConnector() {
        try {
            initMongoDB();

        } catch (MongoException ex) {
            System.out.println("MongoException :" + ex.getMessage());
        }
    }

    public static void initMongoDB() throws MongoException {
        try {
            System.out.println("Connecting to Mongo Db..");
            Mongo mongo;
            mongo = new Mongo("127.0.0.1");
            db = mongo.getDB("tweetDB");

        } catch (UnknownHostException ex) {
            System.out.println("MongoDb Connection Erorr :" + ex.getMessage());
        }
    }
    
    public static DB getDB()
    {
        return db;
    }

}
