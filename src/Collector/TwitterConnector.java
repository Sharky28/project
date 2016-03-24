/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author sharmarke
 */
public class TwitterConnector {

    private static ConfigurationBuilder ob;
    private static TwitterFactory tf;
    private static Twitter tw;

    public static void main(String[] args) {
       
    }

    public  void makeConnection() {
        try {

            ob = new ConfigurationBuilder();
            ob.setJSONStoreEnabled(true);
            ob.setDebugEnabled(true)
                    .setOAuthConsumerKey("EaNDPdbztcAhLEwREWrnXFrbH")
                    .setOAuthConsumerSecret("TfzWIxgGXrgrlpiCAITFMVD1e7i6y6UuP2Z9rHpKIy7ZN35pGQ")
                    .setOAuthAccessToken("4832753560-BLljOptOjTJs8lmbJlvgjSUM9nEQXjyEpKhQart")
                    .setOAuthAccessTokenSecret("cbXs1UMe9mfcCHWdBEj9yV7NgFZ6xgF8w2uzguQs4BTMb");

            tf = new TwitterFactory(ob.build());
            tw = tf.getInstance();

            System.out.println("Connection made");
           // return tw;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public  Twitter getTwitter()
    {
        return tw;
    }

}
