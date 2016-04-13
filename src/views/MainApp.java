/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Collector.TweetCollector;
import sentiment.PreProcessor;
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
    static TweetCollector tweetCollector;
    static PreProcessor process;
   
    private static MainApp app;

    private static List<Status> statuses;

    public MainApp() {
       
       
      //  for (Status statuse : statuses) {
         //   String tweet =PreProcessor.normalizeTweet(statuse.getText());
          //  System.out.println(tweet);
     //   }
     //   System.out.println(">>>>>>>>");
        
       

    }

    public static void main(String[] args) {
        app = new MainApp();
        
         tweetCollector = new TweetCollector();
        
        
        
        
        
//        process();
//        process2();
//        process3();
        
        
    }

   
}
