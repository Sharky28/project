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
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final String intensifiersFile = "C:\\Users\\sharmarke\\OneDrive\\Documents\\year 3\\Project\\TwitterTest\\Twitterfiles\\intensifiers.txt";
    private final SWN3 swn;
    private List<String> negations;
    private Map<String, Double> intensifiers;
    HashMap<String, Double> dictionary;
    double neg = 1.0;
    int intens=0;
    double[] intensifierarray = new double[20];

    public SentiWordNetManager() {
        swn = new SWN3(path);
        this.dictionary = swn.getDictionary();

        negations = new ArrayList<String>();
        intensifiers = new HashMap<String, Double>();

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

    public double calculateSentancescore(String tweet) {

        double score = 0.0d;

        StringTokenizer tokenizer = new StringTokenizer(tweet);
        while (tokenizer.hasMoreElements()) {
            String nextWord = (String) tokenizer.nextElement();
            if(intensifiers.get(nextWord)!=null)
                    {
                        //storing each intensifying word in an array to handle cases like "very very good"
                     intensifierarray[intens++] = intensifiers.get(nextWord);

                    }
            if (dictionary.get(nextWord) != null) {
                for (int a = 0; a < intens; a++) {
                    //the value of the next words sentiment is multiplied by each intensifier 
                    score = score+(dictionary.get(nextWord)*intensifierarray[a]);
                    intensifierarray[a]=0.0; // empty the array so as to not influence later features
                }
                // always multiply by the value of neg , should be 1.0 if no negations found
                score = score + (neg * dictionary.get(nextWord));
                neg = 1;//ensures it stays one 
 
            } else if (negations.contains(nextWord)) {
                //negation found so make the value of neg a negative 
                neg = (-1.0) * neg;
            }
        }
        //trims the double
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(score));

    }
    

    // to check wheter loaded
    public void printNegations() {

        for (String col : negations) {
            System.out.println(col);
        }
    }

    public void contains(String word) {
        boolean temp = dictionary.containsKey(word);
        System.out.println("Dictionary contains :" + word + ", " + temp
                + " and has the value of :" + dictionary.get(word));
    }

    public void removeNegationsFromDict() {
        for (String negation : negations) {
            if (dictionary.containsKey(negation)) {
                dictionary.remove(negation);
            }
        }
    }
    
    public void removeIntensifiersFromDict() {
        Collection<String> coll = intensifiers.keySet();
        for (String negation : coll) {
            if (dictionary.containsKey(negation)) {
                dictionary.remove(negation);
            }
        }
    }

    public void printIntensifiers() {
        Collection<Double> col = intensifiers.values();
        for (Double val : col) {
            System.out.println(val);
        }
    }

    public void loadIntensifiers() {
        String lines = "";
        try {
            BufferedReader csv2 = new BufferedReader(new FileReader(intensifiersFile));
            int x = 0;
            while ((lines = csv2.readLine()) != null) {
                String s = "";

                String[] parts = lines.split("\t");

                intensifiers.put(parts[0], (Double) Double.parseDouble(parts[1]));
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            Logger.getLogger(SentiWordNetManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
