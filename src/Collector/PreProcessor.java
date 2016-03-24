/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sharmarke
 */
public class PreProcessor {

    public static String normalizeTweet(String tweet) {
        String s = tweet.replaceAll("https?://\\S+\\s?", "");
        s=s.replaceAll("ftps?://\\S+\\s?", "");
        s = s.replaceAll("@\\w+|#\\w+|\\bRT\\b", "")
                .replaceAll("\n", " ")
                .replaceAll("[^\\p{L}\\p{N} ]+", " ")
                .replaceAll(" +", " ")
                .trim();

        return s;
    }
    
    

    

    public static String removeDuplicates(String atweet) {

        String tweet = "" + atweet.charAt(0);
        int count = 1;
        for (int i = 1; i < atweet.length(); i++) {
            if (atweet.charAt(i) == atweet.charAt(i - 1)) {
                count++;
                if (count < 3) {
                    tweet = tweet + atweet.charAt(i);
                }
            } else {
                tweet = tweet + atweet.charAt(i);
                count = 1;
            }
        }
        return tweet;
    }
    
   
}
