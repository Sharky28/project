/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import stock.Stock;

/**
 *
 * @author sharmarke
 */
public class Sentiment140 {

    CSVReader reader;
    private static String pathtofile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\Sentiment140.csv";
    List<Tweet> tweets;
    final DateFormat formatter = new SimpleDateFormat(("EEE MMM dd HH:mm:ss Z yyyy"), new Locale("us"));

    public Sentiment140() {
        tweets = new ArrayList<Tweet>();
        loadTweets();
    }

    public void loadTweets() {

        try {
            reader = new CSVReader(new FileReader(pathtofile));

            String[] record = null;

            while ((record = reader.readNext()) != null) {
                Tweet tweet = new Tweet();
                String dateString = record[2];
                //Mon Apr 06 22:19:49 PDT 2009           
                Date date = formatter.parse(dateString);
                tweet.setDate(date);
                tweet.setTweetTxt(record[5]);
                tweets.add(tweet);
//                System.out.println(tweet.toString());

            }

        } catch (Exception e) {
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Sentiment140.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public List<Tweet> getTweetObjects() {
        return this.tweets;
    }

    public static void main(String[] args) {
        Sentiment140 s = new Sentiment140();
    }
}
