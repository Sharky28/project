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
import java.util.ArrayList;
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
        return score;
    }

    public double calculateSentancescore(List<String> sentance) {
        double score = swn.extractSentence(sentance);
        return score;
    }
}
