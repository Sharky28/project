/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
     DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public Sentiment140() throws IOException {
        tweets = new ArrayList<Tweet>();
//              loadTweets();
      loadTweets2();
//              createNewCsv();
    }

    public void loadTweets() {

        try {
            reader = new CSVReader(new FileReader(pathtofile));

            String[] record = null;

            while ((record = reader.readNext()) != null) {
                Tweet tweet = new Tweet();
                String dateString = record[0];
                //Mon Apr 06 22:19:49 PDT 2009           
                Date date = formatter.parse(dateString);
                tweet.setDate(date);
                tweet.setTweetTxt(record[1]);
                tweets.add(tweet);
                System.out.println(tweet.toString());

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
//    public void createNewCsv() throws IOException {
//
//        FileWriter pw = new FileWriter("C:\\Users\\sharmarke\\Documents\\data2.csv");
//        Iterator s = tweets.iterator();
//        if (s.hasNext() == false) {
//            System.out.println("Empty");
//        }
//        while (s.hasNext()) {
//            Tweet current = (Tweet) s.next();
//            System.out.println(current.toString() + "\n");
//            pw.append(current.getDate().toString());
//            pw.append(",");
//            pw.append(current.getTweetTxt());
//            pw.append("\n");
//        }
//        pw.flush();
//        pw.close();
//
//    }

    public void loadTweets2() {
        try {
            reader = new CSVReader(new FileReader("C:\\Users\\sharmarke\\Documents\\data3.csv"));

            String[] record = null;

            while ((record = reader.readNext()) != null) {
                String dateString = record[0];
 //               System.out.println(dateString);
                String tweet = record[1];
                Tweet tObject = new Tweet();              
                Date date = formatter.parse(dateString);
                tObject.setDate(date);
                tObject.setTweetTxt(tweet);
//                System.out.println(tObject.toString());
                tweets.add(tObject);

            }
        } catch (IOException ex) {
            Logger.getLogger(Sentiment140.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Sentiment140.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Tweet> getTweetObjects() {
        return this.tweets;
    }
//    
//    public Date createDateFromString(String input)
//    {
//         DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//    }

    public static void main(String[] args) throws IOException {
        Sentiment140 s = new Sentiment140();

    }
}
