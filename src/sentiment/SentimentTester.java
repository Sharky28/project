/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sharmarke
 */
public class SentimentTester {

    private final SentimentAnalyser analyzer;
    private final String pathToPositiveSample = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\positive.txt";
    private final String pathToNegativeSample = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\negative.txt";
    private List<String> positiveTweets;
    private List<String> negativeTweets;

    public SentimentTester() {
        analyzer = new SentimentAnalyser();
        loadPositiveTweets();
        //testPositives();
        loadNegativeTweets();
        testNegtatives();

    }

    public String calculateSentiment(String tweet) {
        double score = analyzer.calculateTweetPolarity(tweet);
        return classForScore(score);
    }

    public static void main(String[] args) {
        SentimentTester test = new SentimentTester();

    }

    public String classForScore(Double score) {
        String result = "neutral";
        if (score > 0) {
            result = "positive";

        } else if (score < 0) {
            result = "negative";
        }
        return result;
    }

    public void loadPositiveTweets() {
        BufferedReader reader;
        positiveTweets = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(pathToPositiveSample));

            String line = null;

            while ((line = reader.readLine()) != null) {
                positiveTweets.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void loadNegativeTweets() {
        BufferedReader reader;
        negativeTweets = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(pathToNegativeSample));

            String line = null;

            while ((line = reader.readLine()) != null) {
                negativeTweets.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void testPositives() {
        int pCount = 0;
        int nCount = 0;
        for (String positiveTweet : positiveTweets) {
            if (classForScore(analyzer.calculateTweetPolarity(positiveTweet)).equals("positive")) {
                pCount++;
            } else if (classForScore(analyzer.calculateTweetPolarity(positiveTweet)).equals("negative")) {
                nCount++;
            }
        }
        System.out.println("Amount of positives :" + pCount
                + "\nAmount of negative :" + nCount);
    }

    public void testNegtatives() {
        int nCount = 0;
        int pCount = 0;
        for (String negativeTweet : negativeTweets) {
            if (classForScore(analyzer.calculateTweetPolarity(negativeTweet)).equals("negative")) {
                nCount++;
            } else if (classForScore(analyzer.calculateTweetPolarity(negativeTweet)).equals("positive")) {
                pCount++;
            }
        }
        System.out.println("Amount of negatives :" + nCount
                + "\nAmount of positives :" + pCount);
    }

}
