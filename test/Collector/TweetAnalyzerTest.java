/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sharmarke
 */
public class TweetAnalyzerTest {

    public TweetAnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class TweetAnalyzer.
     */
    @Test
    public void testMain() {

    }

    /**
     * Test of calculateTweetScore method, of class TweetAnalyzer.
     */
    @Test
    public void testCalculateTweetScore() {
        System.out.println("calculateTweetScore");
        String tw = "great";
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double epsilon = 0.001;
        double expectedResult = 0.068;
        assertEquals(expectedResult, result, epsilon);
    }

    @Test
    public void testCalculateTweetScore_ToFail() {
        System.out.println("calculateTweetScore");
        String tw = "great";
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double expectedResult = 0.066; // changed value
        assertFalse(result == expectedResult);
    }

    @Test
    public void testCalculateTweetScoreWithIntensifier() {
        System.out.println("calculateTweetScore");
        String tw = " very great";
        double epsilon = 0.01;
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double expectedResult = (0.068 * 1.5) + 0.068; //very has a value of 1.5
        assertEquals(expectedResult, result, epsilon);

    }

    @Test
    public void testCalculateTweetScoreWithIntensifier_ToFail() {
        System.out.println("calculateTweetScore");
        String tw = " very great";
        double epsilon = 0.01;
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double expectedResult = 0.068; //no intensifiers taken into account
        assertFalse(result == expectedResult);

    }

    @Test
    public void testCalculateTweetScoreWithNegation() {
        System.out.println("calculateTweetScore");
        String tw = " not great";
        double epsilon = 0.01;
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double expectedResult = (-1.0) * 0.068; //should be -0.068
        assertEquals(expectedResult, result, epsilon);

    }

    @Test
    public void testCalculateTweetScoreWithNegation_ToFail() {
        System.out.println("calculateTweetScore");
        String tw = "great";
        double epsilon = 0.01;
        double result = TweetAnalyzer.calculateTweetScore(tw);
        double expectedResult = (-1.0) * 0.068; //testing to see if negated if not negation found
        assertFalse(result == expectedResult);

    }

}
