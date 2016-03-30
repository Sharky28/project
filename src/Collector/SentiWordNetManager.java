/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sharmarke
 */
public class SentiWordNetManager {

    private final String path = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\SentiWordNet_3.0.0_20130122.txt";
    private final String negationsFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\negations.txt";
    private final SWN3 swn;
    private List<String> negations;

    public SentiWordNetManager() {
        swn = new SWN3(path);
        negations = new ArrayList<String>();

        String line = "";
        try {
            BufferedReader csv = new BufferedReader(new FileReader(negationsFile));
            while ((line = csv.readLine()) != null) {
                negations.add(line);

            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            Logger.getLogger(SentiWordNetManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SWN3 getSwn3() {
        return this.swn;
    }

    public double calculateWordScore(String word) {

        double score = swn.extract(word);
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(score));
    }

    public double calculateSentancescore(List<String> sentance) {
        HashMap<String, Double> dictionary = swn.getDictionary();
        double score = 0.0d;

        for (String word : sentance) {
            //check not a negation word
            double neg =-1.0;
            if (!negations.contains(word)) {
                if (dictionary.get(word) != null) {
                    score += dictionary.get(word);
                }
            }
            if(negations.contains(word)){
                
                double negatedScore = dictionary.get(word)*neg;
                score += negatedScore;
            }

        }

        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(score));

    }
    
    public void handleNegation(List<String> input)
    {
        double[] originalScore = new double[input.size()];
        double [] newScores = new double[input.size()];
        double newScore =0.0;
        double neg =-1;
        String[] words = new String[input.size()];
        for (int i = 0; i < input.size(); i++) {
            String temp = input.get(i);
            words[i]=temp;
        }
        for (int x = 0; x < input.size(); x++) {
            if(!negations.contains(words[x]))
            {
                originalScore[x]= swn.getDictionary().get(words[x]);
                System.out.println(Arrays.toString(originalScore));
            }
            if(negations.contains(words[x]))
            {
               String wordToBeFlipped = words[x]+1;
               newScore= swn.getDictionary().get(wordToBeFlipped)*neg;
               newScores[x+1] = newScore;
                System.out.println(Arrays.toString(newScores));
            }
        }
       
    }
}
