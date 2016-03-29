/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.Regexp;

/**
 *
 * @author sharmarke
 */
public class tester {

    public static void main(String[] args) {
        String tweet = "@wpmoneymaker -  bad";
        //   Pattern p = Pattern.compile("http://*https://*www.*ftp://*ssh://*@");
        // Matcher m = p.matcher(tweet);
        // boolean b = m.matches();
        // System.out.println(b);
        SentiWordNetManager sm = new SentiWordNetManager();
       
        String s = normalizeTweet(tweet);
         System.out.println(sm.calculateWordScore(s));
        System.out.println(s);

        String t = removeURL(tweet);
      //  System.out.println(t);
        //removes "http://foo" "https://bar"
        // percentageChange((float) 6201.1, (float) 6189.6);

    }

    public static String removeURL(String atweet) { // atweet is the given tweet to be preprocessed
        String newtweet = "";
        String ht = "http://";
        String hts = "https://";
        String ww = "www.";
        String ftp = "ftp://";
        String ss = "ssh://";
        StringTokenizer tweet = new StringTokenizer(atweet);
        while (tweet.hasMoreElements()) {
            String nextword = (String) tweet.nextElement();
            if ((!nextword.startsWith(ht)) && (!nextword.startsWith(hts))
                    && (!nextword.startsWith(ww))
                    && (!nextword.startsWith(ftp))
                    && (!nextword.startsWith(ss))
                    && (!nextword.startsWith("@"))) {
                newtweet = newtweet + nextword + " ";
            }
        }
        return newtweet;
    }

    public static String normalizeTweet(String tweet) {
        String s = tweet;
        s = s.replaceAll("https?://\\S+\\s?", "")
                .replaceAll("ftps?://\\S+\\s?", "")
                .replaceAll("@\\w+|#\\w+|\\bRT\\b", "")
                .replaceAll("\n", " ")
                .replaceAll("[^\\p{L}\\p{N} ]+", " ")
                .replaceAll(" +", " ")
                .replaceAll("(.)\\1+", "$1")
                .trim();

        return s;
    }

    public static void percentageChange(float x, float y) {
        if (x < y) {
            float increace = y - x;
            float pIncreace = (increace / x) * 100;
            System.out.println("Percentage of increace is :+" + pIncreace + "%");
        } else if (y < x) {
            float decreace = x - y;
            float pDecreace = (decreace / x) * 100;
            System.out.println("Percentage of decreace is :-" + pDecreace + "%");
        }
    }

}
