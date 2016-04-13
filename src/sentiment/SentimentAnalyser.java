/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentiment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author sharmarke
 */
public class SentimentAnalyser {

    private Lexicons lexicon;
    private List<Double> sessionScores;
    private PreProcessor processor;

    public SentimentAnalyser() {
        processor = new PreProcessor();
        lexicon = new Lexicons();
        sessionScores = new ArrayList<Double>();
    }

    public SentimentAnalyser(Lexicons lex) {
        this.lexicon = lex;
    }

    public void addLexicon(Lexicons lex) {
        this.lexicon = lex;
    }

    public  double calculateTweetPolarity(String unProcessedTweet) {

       String tweet = processor.normalizeTweet(unProcessedTweet);
        
        double score = 0.0d;
        double negator = 1.0;
        int intens = 0;
        double[] intensifierarray = new double[20];

        StringTokenizer tokenizer = new StringTokenizer(tweet);
        while (tokenizer.hasMoreElements()) {
            String nextWord = (String) tokenizer.nextElement();
            if (lexicon.getIntensifiers().get(nextWord) != null) {
                //storing each intensifying word in an array to handle cases like "very very good"
                intensifierarray[intens++] = lexicon.getIntensifiers().get(nextWord);

            }
            if (lexicon.getDictionary().get(nextWord) != null) {
                for (int a = 0; a < intens; a++) {
                    //the value of the next words sentiment is multiplied by each intensifier
                    score = score + (lexicon.getDictionary().get(nextWord) * intensifierarray[a]);
                    intensifierarray[a] = 0.0; // empty the array so as to not influence later features
                }
                // always multiply by the value of neg , should be 1.0 if no negations found
                score = score + (negator * lexicon.getDictionary().get(nextWord));
                negator = 1;//ensures it stays one

            } else if (lexicon.getNegations().contains(nextWord)) {
                //negation found so make the value of neg a negative
                negator = (-1.0) * negator;
            }
        }
        // collects all scores in order to calculate average
//        sessionScores.add(score);
        //trims the double
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(score));

    }
    
    public List<Double> getSessionScores()
    {
        return  sessionScores;
    }
}
